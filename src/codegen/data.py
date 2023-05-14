from __future__ import annotations # for recursive type annotations
from dataclasses import dataclass, field, is_dataclass
from typing import List, Optional
from enum import Enum
from strenum import StrEnum

from swagger import ApiCategoryEnum


def is_reserved(ident: str) -> bool:
    # should it be escaped?
    # https://www.scala-lang.org/files/archive/spec/2.11/01-lexical-syntax.html#identifiers
    reserved_words = [
        "abstract", "case", "catch", "class", "def",
        "do", "else", "extends", "false", "final",
        "finally", "for", "forSome", "if", "implicit",
        "import", "lazy", "macro", "match", "new",
        "null", "object", "override", "package", "private",
        "protected", "return", "sealed", "super", "this",
        "throw", "trait", "try", "true", "type",
        "val", "var", "while", "with", "yield",
        "_", ":", "=", "=>", "<-", "<:", "<%", ">:", "#", "@",
        "⇒", "←"
    ]

    # this is _technically_ incorrect
    # python's implementation obviously differs
    # but eh, good enough init?
    is_reserved = (ident in reserved_words) or not ident.isidentifier() 
    return is_reserved


# TODO: perhaps this should be determined by the templating logic
class SecurityEnum(StrEnum):
    NoAuth = "NoAuthentication"
    AccessToken = "AccessTokenAuthentication"
    HomeserverAccessToken = "HomeserverAccessTokenAuthentication"

class PrimitiveTypeEnum(StrEnum):
    Int = "Int"
    Double = "Double"
    String = "String"
    Boolean = "Boolean"
    Unit = "Unit"

    # formated types
    Int32 = "Int"
    Int64 = "Long"
    Float = "Float"

@dataclass
class BaseType:
    def to_polymorphic(self) -> PolymorphicType:
        return PolymorphicType.from_basic(self)

@dataclass
class OptionType(BaseType):
    inner_type: PolymorphicType

@dataclass
class PrimitiveType(BaseType):
    type: PrimitiveTypeEnum

@dataclass
class ListyType(BaseType):
    inner_type: PolymorphicType

@dataclass
class GenericType(BaseType):
    pass

@dataclass
class GenericValueType(BaseType):
    pass

@dataclass
class ModelType(BaseType):
    model: PolymorphicModel

@dataclass
class UnitType(BaseType):
    pass

@dataclass
class MapType(BaseType):
    inner_type: PolymorphicType

@dataclass
class UnionType(BaseType):
    inner_types: List[PolymorphicType]



@dataclass
class PolymorphicType:
    data: BaseType
    # used for polymorphism
    is_primitive: bool = False
    is_model: bool = False
    is_listy: bool = False
    is_union: bool = False
    is_option: bool = False
    is_generic: bool = False
    is_generic_value: bool = False
    is_unit: bool = False
    is_map: bool = False

    @staticmethod
    def from_basic(data: BaseType) -> PolymorphicType:
        if isinstance(data, PrimitiveType):
            return PolymorphicType(data, is_primitive=True)
        if isinstance(data, GenericType):
            return PolymorphicType(data, is_generic=True)
        if isinstance(data, ModelType):
            return PolymorphicType(data, is_model=True)
        if isinstance(data, UnitType):
            return PolymorphicType(data, is_unit=True)
        if isinstance(data, ListyType):
            return PolymorphicType(data, is_listy=True)
        if isinstance(data, MapType):
            return PolymorphicType(data, is_map=True)
        if isinstance(data, UnionType):
            return PolymorphicType(data, is_union=True)
        if isinstance(data, GenericValueType):
            return PolymorphicType(data, is_generic_value=True)
        if isinstance(data, OptionType):
            return PolymorphicType(data, is_option=True)
        assert False, "could not determine polymorphic type"


@dataclass
class Argument:
    description: Optional[str]
    name: str
    type: PolymorphicType
    escape: bool
    required: bool  #should the transformer, or the template make this into an Option??

@dataclass
class StatusCodeRange:
    start: int
    end: int
@dataclass
class ResponseType:
    status_code_range: StatusCodeRange
    type: PolymorphicType

@dataclass
class Operation:
    deprecated: bool
    summary: Optional[str]
    description: Optional[str]

    operation_id: str
    endpoint: str
    http_method: str
    response_type: PolymorphicType # the responses trait 
    response_types: List[ResponseType] # the individual responses
    path_args: list[Argument]
    query_args: list[Argument]
    header_args: list[Argument]
    has_body: bool
    body_type: PolymorphicType
    container_model: PolymorphicModel
    security: SecurityEnum


@dataclass
class BaseModel:
    model_name: str
    inner_models: Optional[list[PolymorphicModel]] = None
    parent: Optional[PolymorphicModel] = None
    description: Optional[str] = None
    
    def to_polymorphic(self) -> PolymorphicModel:
        return PolymorphicModel.from_basic(self)

@dataclass
class PolymorphicModel:
    data: BaseModel

    is_container_model: bool = False
    is_data_model: bool = False
    is_string_enum: bool = False
    is_responses_trait: bool = False
    is_response_box: bool = False
    @staticmethod
    def from_basic(data: BaseModel) -> PolymorphicModel:
        if isinstance(data, DataModel):
            return PolymorphicModel(data, is_data_model=True)
        if isinstance(data, ContainerModel):
            return PolymorphicModel(data, is_container_model=True)
        if isinstance(data, StringEnumModel):
            return PolymorphicModel(data, is_string_enum=True)
        if isinstance(data, ResponseBoxModel):
            return PolymorphicModel(data, is_response_box=True)
        if isinstance(data, ResponsesTraitModel):
            return PolymorphicModel(data, is_responses_trait=True)
        assert False, "could not determine polymorphic type"

@dataclass
class ResponsesTraitModel(BaseModel):
    pass

@dataclass
class ResponseBoxModel(BaseModel):
    #TODO: these are NOT Optional, dataclass inheritance is weird
    boxes: Optional[PolymorphicType] = None 
    of_response_trait: Optional[PolymorphicModel] = None 

@dataclass
class DataModel(BaseModel):
    of_response_trait: Optional[PolymorphicModel] = None 
    fields: list[Argument] = field(default_factory=list) #TODO: fix dataclass composition, and make this mandatory
    additional_type: Optional[PolymorphicType] = None
    #composites: Optional[List[PolymorphicModel]] = None
    compositions: Optional[List[Composition]] = None
    composites_children: Optional[List[PolymorphicModel]] = None

    defined_in_path: Optional[str] = None

    def count_fields(self) -> int:
        return len(self.fields) + sum([len(comp.fields) for comp in self.compositions or [] ])

@dataclass
class Composition:
    origin: PolymorphicModel
    fields: List[Argument]

@dataclass
class ContainerModel(BaseModel):
    pass


# PLACEHOLDER
@dataclass
class StringEnumModel(BaseModel):
    values: List[str] = field(default_factory=list) #TODO: fix dataclass composition, and make this mandatory

@dataclass
class Definition:
    name: str
    model: PolymorphicModel

@dataclass
class Module:
    module_name: str
    operations: list[Operation]
    defined_in_path: str


@dataclass
class EventSchemas:
    container: PolymorphicModel
    #models: List[PolymorphicModel]

@dataclass
class GenerationApiCategory:
    category: ApiCategoryEnum
    modules: List[Module]
    display_name: str

    @staticmethod
    def display_name_from_type(type: ApiCategoryEnum) -> str:
        display = str(type).lower().replace('-','')
        return display

