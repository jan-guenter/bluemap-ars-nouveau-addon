/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arsnouveau.adapter.bluemap522;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.janguenter.bluemap.arsnouveau.adapter.bluemap522.RendererDataRegistry.StateRoute;
import io.github.janguenter.bluemap.arsnouveau.model.InstalledGeoModel.Vec3;
import org.junit.jupiter.api.Test;

class InstalledGeoMeshEmitterTest {

    private static final double DELTA = 1.0E-9D;

    @Test
    void appliesTheSixInstalledUpFacingVariantTransforms() {
        Vec3 point = new Vec3(0D, 1D, 0D);

        assertVector(InstalledGeoMeshEmitter.transformPoint(point, "up"),
                0.5D, 1D, 0.5D);
        assertVector(InstalledGeoMeshEmitter.transformPoint(point, "down"),
                0.5D, 0D, 0.5D);
        assertVector(InstalledGeoMeshEmitter.transformPoint(point, "north"),
                0.5D, 0.5D, 1D);
        assertVector(InstalledGeoMeshEmitter.transformPoint(point, "south"),
                0.5D, 0.5D, 0D);
        assertVector(InstalledGeoMeshEmitter.transformPoint(point, "east"),
                1D, 0.5D, 0.5D);
        assertVector(InstalledGeoMeshEmitter.transformPoint(point, "west"),
                0D, 0.5D, 0.5D);
    }

    @Test
    void appliesRelayAndExactTurretTransforms() {
        assertEquals(
                new Vec3(0.5D, 1D, 0.5D),
                InstalledGeoMeshEmitter.transformPoint(
                        new Vec3(0D, 1D, 0D), StateRoute.RELAY, "north"
                )
        );
        assertVector(InstalledGeoMeshEmitter.transformPoint(
                new Vec3(0D, 1D, 0D), StateRoute.TURRET, "north"
        ), 0.5D, 1D, 0.5D);
        assertVector(InstalledGeoMeshEmitter.transformPoint(
                new Vec3(0D, 1D, 0D), StateRoute.TURRET, "up"
        ), 0.5D, 0.5D, 1D);
        assertVector(InstalledGeoMeshEmitter.transformPoint(
                new Vec3(1D, 1D, 0D), StateRoute.ROTATING_TURRET, "north"
        ), 0.5D, 1D, -0.5D);
        assertVector(InstalledGeoMeshEmitter.transformPoint(
                new Vec3(1D, 1D, 0D), StateRoute.ROTATING_TURRET, "east"
        ), 0.5D, 1D, -0.5D);
    }

    private static void assertVector(Vec3 actual, double x, double y, double z) {
        assertEquals(x, actual.x(), DELTA);
        assertEquals(y, actual.y(), DELTA);
        assertEquals(z, actual.z(), DELTA);
    }
}
