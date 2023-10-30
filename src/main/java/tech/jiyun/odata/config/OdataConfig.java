package tech.jiyun.odata.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.metamodel.EmbeddableType;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Attribute;

import org.apache.olingo.commons.api.ex.ODataException;
import org.apache.olingo.server.api.debug.DefaultDebugSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.sap.olingo.jpa.metadata.core.edm.mapper.api.JPAEdmNameBuilder;
import com.sap.olingo.jpa.metadata.core.edm.mapper.impl.JPADefaultEdmNameBuilder;
import com.sap.olingo.jpa.processor.core.api.JPAODataRequestContext;
import com.sap.olingo.jpa.processor.core.api.JPAODataServiceContext;
import com.sap.olingo.jpa.processor.core.api.JPAODataSessionContextAccess;
import com.sap.olingo.jpa.processor.core.api.example.JPAExampleCUDRequestHandler;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Configuration
public class OdataConfig {
  @Bean
  public JPAODataSessionContextAccess sessionContext(@Autowired final EntityManagerFactory emf)
      throws ODataException {

    return JPAODataServiceContext.with()
        .setPUnit("odata") // (1)
        .setEntityManagerFactory(emf)
        .setTypePackage("tech.jiyun.odata") // (2)
        .setRequestMappingPath("/odata/v1") // (3)
        .setEdmNameBuilder(new APINameBuilder("tech.jiyun.odata.entity")) // (1)
        .build();
  }

  @Bean
  @Scope(scopeName = SCOPE_REQUEST)
  public JPAODataRequestContext requestContext() {

    return JPAODataRequestContext.with()
        .setCUDRequestHandler(new JPAExampleCUDRequestHandler()) // (1)
        .setDebugSupport(new DefaultDebugSupport()) // (2)
        .build();
  }
}

class APINameBuilder implements JPAEdmNameBuilder {
  private final JPAEdmNameBuilder defaultNameBuilder;

  APINameBuilder(final String punit) {
    defaultNameBuilder = new JPADefaultEdmNameBuilder(punit); // (1)
  }

  @Override
  public String buildComplexTypeName(final EmbeddableType<?> jpaEmbeddedType) {
    return defaultNameBuilder.buildComplexTypeName(jpaEmbeddedType);
  }

  @Override
  public String buildContainerName() {
    return defaultNameBuilder.buildContainerName();
  }

  @Override
  public String buildEntitySetName(final String entityTypeName) {
    return defaultNameBuilder.buildEntitySetName(entityTypeName); // (2)
  }

  @Override
  public String buildEntityTypeName(final EntityType<?> jpaEntityType) {
    return defaultNameBuilder.buildEntityTypeName(jpaEntityType);
  }

  @Override
  public String buildEnumerationTypeName(final Class<? extends Enum<?>> javaEnum) {
    return defaultNameBuilder.buildEnumerationTypeName(javaEnum);
  }

  @Override
  public String buildNaviPropertyName(final Attribute<?, ?> jpaAttribute) {
    return jpaAttribute.getName(); // defaultNameBuilder.buildNaviPropertyName(jpaAttribute);
  }

  @Override
  public String buildOperationName(final String internalOperationName) {
    return defaultNameBuilder.buildOperationName(internalOperationName);
  }

  @Override
  public String buildPropertyName(final String jpaAttributeName) {
    return jpaAttributeName; // defaultNameBuilder.buildPropertyName(jpaAttributeName);
  }

  @Override
  public String getNamespace() {
    return defaultNameBuilder.getNamespace();
  }
}