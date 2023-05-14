import swagger
from codegen.data import SecurityEnum


def transform_security_type(operation: swagger.Operation) -> SecurityEnum: 
    securities = operation.security
    if securities is None:
        return SecurityEnum.NoAuth
    assert len(securities) == 1, f"more or less than one security given in {securities}"
    security = securities[0]

    if isinstance(security, swagger.HomeserverAccessSecurity):
        return SecurityEnum.HomeserverAccessToken
    if isinstance(security, swagger.AccessTokenSecurity):
        return SecurityEnum.AccessToken

    assert False, f"unknown security scheme {security}"
