package learn.schema;

import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;


public class UserBeanDefinitionParser implements BeanDefinitionParser {

    @Nullable
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        builder.addPropertyValue("age",element.getAttribute("age"));
        builder.addPropertyValue("name",element.getAttribute("name"));
        BeanDefinitionRegistry beanDefinitionRegistry = parserContext.getRegistry();
        BeanDefinition beanDefinition = builder.getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("userr",beanDefinition);
        return beanDefinition;
    }
}
