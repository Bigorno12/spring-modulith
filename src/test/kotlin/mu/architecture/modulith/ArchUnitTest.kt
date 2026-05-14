package mu.architecture.modulith

import com.tngtech.archunit.base.DescribedPredicate.alwaysTrue
import com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage
import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption.Predefined
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ArchUnitTest {

    companion object {
        val PROJECT_CLASSES: JavaClasses? = ClassFileImporter()
            .withImportOption(Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("mu.architecture.modulith.")
    }

    @Test
    fun modules_only_depend_on_internal_apis_of_others() {
        val rule = SlicesRuleDefinition.slices()
            .matching("mu.architecture.modulith.(*).*")
            .should()
            .notDependOnEachOther() // Exception: it's allowed to depend on any class in any top-level package = exposed internal api
            .ignoreDependency(alwaysTrue(), resideInAnyPackage("mu.architecture.modulith.*")) // ok to depend on any class in 'shared' or any of its sub-packages
            .ignoreDependency(alwaysTrue(), resideInAnyPackage("mu.architecture.modulith.common.."))

        // crash on any violation
        rule.check(PROJECT_CLASSES)

        // measure distance from ideal
//    assertThat(rule.evaluate(classes).getFailureReport().getDetails()).hasSize(0);

        // record initial violations in /src/test/resources/archunit
        // ❌ FAILS for new violations 🔼
        // ✅ PASSES for same known violations 🟰
        // ✅ PASSES for fewer violations 🔽, and auto-updates the file
//    FreezingArchRule.freeze(rule).check(classes);
    }

    @Test
    fun no_cycles_between_modules() {
        val cycles: List<String> = SlicesRuleDefinition.slices()
            .matching("mu.architecture.modulith.(*).*")
            .should()
            .beFreeOfCycles()
            .evaluate(PROJECT_CLASSES)
            .getFailureReport()
            .details

        Assertions.assertTrue(cycles.isEmpty())
    }

    @Test
    fun module_internal_apis_are_independent() {
        SlicesRuleDefinition.slices()
            .matching("mu.architecture.modulith.(*)") // root package of all slices
            .should().notDependOnEachOther()
            .ignoreDependency(alwaysTrue(), resideInAnyPackage("mu.architecture.modulith.common"))
            .check(PROJECT_CLASSES)
    }
}