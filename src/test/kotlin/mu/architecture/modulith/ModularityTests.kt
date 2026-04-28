package mu.architecture.modulith

import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules
import org.springframework.modulith.docs.Documenter
import org.springframework.modulith.docs.Documenter.CanvasOptions
import org.springframework.modulith.docs.Documenter.DiagramOptions

class ModularityTests {

    companion object {
        val modules: ApplicationModules = ApplicationModules.of(ModulithApplication::class.java);
    }

    @Test
    fun verifiesModularStructured() {
        modules.verify()
    }

    @Test
    fun generateDiagrams() {
        Documenter(modules)
            .writeModulesAsPlantUml()
            .writeIndividualModulesAsPlantUml()
    }

    @Test
    fun generateAsciidoc() {
        val canvasOption = CanvasOptions
            .defaults()

        val docOptions = DiagramOptions
            .defaults()
            .withStyle(DiagramOptions.DiagramStyle.UML)

        Documenter(modules)
            .writeDocumentation(docOptions, canvasOption)
    }
}