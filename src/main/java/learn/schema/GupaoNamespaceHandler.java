package learn.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.context.annotation.AnnotationConfigBeanDefinitionParser;
import org.springframework.context.annotation.ComponentScanBeanDefinitionParser;
import org.springframework.context.config.*;

public class GupaoNamespaceHandler extends NamespaceHandlerSupport{
    @Override
    public void init() {
        this.registerBeanDefinitionParser("usert", new UserBeanDefinitionParser());
    }
}
