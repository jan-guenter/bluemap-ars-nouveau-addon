/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arsnouveau.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

class InstalledGeoCompilerTest {

    private static final String GEO_ROOT = "assets/ars_nouveau/geo/";
    private static final List<GeometryCase> GEOMETRIES = List.of(
            geometry("enchanting_apparatus", InstalledGeoCompiler.ENCHANTING_APPARATUS),
            geometry("imbuement_chamber", InstalledGeoCompiler.IMBUEMENT_CHAMBER),
            geometry("source_relay", InstalledGeoCompiler.SOURCE_RELAY),
            geometry("source_splitter", InstalledGeoCompiler.SOURCE_SPLITTER),
            geometry("source_deposit", InstalledGeoCompiler.SOURCE_DEPOSIT),
            geometry("source_warp", InstalledGeoCompiler.SOURCE_WARP),
            geometry("source_collector", InstalledGeoCompiler.SOURCE_COLLECTOR),
            geometry("basic_spell_turret", InstalledGeoCompiler.BASIC_SPELL_TURRET),
            geometry("spell_turret", InstalledGeoCompiler.SPELL_TURRET),
            geometry("spell_turret_timer", InstalledGeoCompiler.SPELL_TURRET_TIMER)
    );

    @Test
    void compilesEveryExactInstalledMeshDeterministically() throws IOException {
        for (GeometryCase geometry : GEOMETRIES) {
            byte[] bytes = exactEntry(geometry.path());
            InstalledGeoModel model = InstalledGeoCompiler.compile(bytes, geometry.contract());

            assertEquals(geometry.contract().quads(), model.quads().size());
            assertEquals(model, InstalledGeoCompiler.compile(bytes, geometry.contract()));
            assertTrue(model.quads().stream().flatMap(quad -> java.util.stream.Stream.of(
                    quad.first(), quad.second(), quad.third(), quad.fourth()
            )).allMatch(vertex -> finite(vertex.position())
                    && Float.isFinite(vertex.u()) && Float.isFinite(vertex.v())));
        }
    }

    @Test
    void rejectsChangedInstalledSchemaAndWrongContract() throws IOException {
        GeometryCase apparatus = GEOMETRIES.getFirst();
        String geometry = new String(exactEntry(apparatus.path()), StandardCharsets.UTF_8);
        byte[] changed = geometry.replace("\"1.12.0\"", "\"9.99.0\"")
                .getBytes(StandardCharsets.UTF_8);

        assertThrows(IllegalArgumentException.class, () -> InstalledGeoCompiler.compile(
                changed, apparatus.contract()
        ));
        assertThrows(IllegalArgumentException.class, () -> InstalledGeoCompiler.compile(
                exactEntry(apparatus.path()), InstalledGeoCompiler.SOURCE_RELAY
        ));
    }

    private static GeometryCase geometry(
            String name,
            InstalledGeoCompiler.Contract contract
    ) {
        return new GeometryCase(GEO_ROOT + name + ".geo.json", contract);
    }

    private static byte[] exactEntry(String path) throws IOException {
        String property = System.getProperty("arsNouveauJar");
        Assumptions.assumeTrue(property != null && !property.isBlank());
        try (ZipFile zip = new ZipFile(Path.of(property).toFile())) {
            ZipEntry entry = zip.getEntry(path);
            Assumptions.assumeTrue(entry != null && !entry.isDirectory());
            return zip.getInputStream(entry).readAllBytes();
        }
    }

    private static boolean finite(InstalledGeoModel.Vec3 value) {
        return Double.isFinite(value.x())
                && Double.isFinite(value.y())
                && Double.isFinite(value.z());
    }

    private record GeometryCase(
            String path,
            InstalledGeoCompiler.Contract contract
    ) {
    }
}
