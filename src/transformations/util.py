from typing import TypeVar
import re

from codegen.data import OptionType, PolymorphicModel, PolymorphicType

T = TypeVar("T")
def PLACEHOLDER(x: T) -> T:
    return x



def flatten_2d(outer: list[list[T]]) -> list[T]:
    return [item for sublist in outer for item in sublist]


# "Some cool name" => "SomeCoolName"
def to_model_name(s: str) -> str:
    word_regex =  r'[a-zA-Z][a-zA-Z0-9]*'
    def capitalize_first_char(s: str) -> str:
        return s[0].capitalize() + s[1:]

    return "".join(map(capitalize_first_char, re.findall(word_regex, s)))


# avoid child/parent inconsistent state
# TODO: make this less imperative
def attach_model(model: PolymorphicModel, parent: PolymorphicModel):
    model.data.parent = parent
    if parent.data.inner_models is None:
        parent.data.inner_models = [model]
    else:
        parent.data.inner_models.append(model)

def wrap_in_option(is_required: bool, t: PolymorphicType) -> PolymorphicType:
    # wrap in option if not required
    wrapped = OptionType(t).to_polymorphic() if not is_required else t
    return wrapped
