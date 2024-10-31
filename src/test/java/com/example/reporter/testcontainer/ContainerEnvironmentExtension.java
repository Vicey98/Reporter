package com.example.reporter.testcontainer;

import org.junit.jupiter.api.extension.*;

public class ContainerEnvironmentExtension implements ParameterResolver, BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext context) throws ParameterResolutionException {
        return Environment.class.equals(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext context) throws ParameterResolutionException {
        ExtensionContext engineContext = context.getRoot();
        ExtensionContext.Store store = engineContext.getStore(ExtensionContext.Namespace.GLOBAL);
        return store.getOrComputeIfAbsent(ContainerEnvironmentResource.class);
    }
}
