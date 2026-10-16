package com.petvax.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

class ArchitectureTest {

    private final JavaClasses classes =
            new ClassFileImporter()
                    .importPackages("com.petvax");

    @Test
    void domainShouldNotDependOnApplicationOrInfrastructure() {

        ArchRule rule = noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "..application..",
                        "..infrastructure.."
                );

        rule.check(classes);
    }

    @Test
    void applicationShouldNotDependOnInfrastructure() {

        ArchRule rule = noClasses()
                .that()
                .resideInAPackage("..application..")
                .should()
                .dependOnClassesThat()
                .resideInAPackage("..infrastructure..");

        rule.check(classes);
    }

    @Test
    void domainShouldNotDependOnSpringOrJpa() {

        ArchRule rule = noClasses()
                .that()
                .resideInAPackage("..domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "org.springframework..",
                        "jakarta.persistence.."
                );

        rule.check(classes);
    }
    @Test
    void persistenceAdaptersShouldEndWithAdapter() {

        ArchRule rule = classes()
                .that()
                .resideInAPackage("..infrastructure.persistence.adapter..")
                .should()
                .haveSimpleNameEndingWith("Adapter");

        rule.check(classes);
    }

    @Test
    void jpaRepositoriesShouldEndWithRepository() {

        ArchRule rule = classes()
                .that()
                .resideInAPackage("..infrastructure.persistence.repository..")
                .should()
                .haveSimpleNameEndingWith("Repository");

        rule.check(classes);
    }
}