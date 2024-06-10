package org.acme;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.hibernate.envers.RevisionListener;

import io.quarkus.security.identity.SecurityIdentity;
import io.vertx.core.http.HttpServerRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
public class CustomRevisionListener implements RevisionListener {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    @Named("vertxRequest")
    HttpServerRequest request; // Injetando o objeto HttpServerRequest diretamente

    @Produces
    @RequestScoped
    public HttpServerRequest produceHttpRequest() {
        return request;
    }

    @Override
    public void newRevision(Object revisionEntity) {
        Log log = (Log) revisionEntity;
        log.setCreationDate(LocalDateTime.ofInstant(Instant.ofEpochMilli(log.getTimestamp()), ZoneId.systemDefault()));

        String usermail = "anonymous";
        if (securityIdentity != null) {
            usermail = securityIdentity.getPrincipal().getName();
        }
        log.setUser(usermail);

        // Verifica se o objeto HttpServerRequest não é nulo antes de acessá-lo
        if (request != null) {
            log.setIp(request.remoteAddress().hostAddress());
        } else {
            log.setIp("unknown");
        }
    }
}
