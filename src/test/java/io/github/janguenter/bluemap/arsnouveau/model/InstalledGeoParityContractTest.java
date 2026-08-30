/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arsnouveau.model;

import io.github.janguenter.bluemap.installedgeo.testing.InstalledGeoParityHarness;
import io.github.janguenter.bluemap.installedgeo.testing.InstalledGeoParityHarness.MeshSnapshot;
import io.github.janguenter.bluemap.installedgeo.testing.InstalledGeoParityHarness.VertexSnapshot;
import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoCompiler;
import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoModel;
import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoModel.Quad;
import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoModel.Vertex;

class InstalledGeoParityContractTest extends InstalledGeoParityHarness {

    private static final InstalledGeoCompiler.Contract SYNTHETIC_CONTRACT =
            new InstalledGeoCompiler.Contract(20, 36, 192);

    @Override
    protected MeshSnapshot compile(byte[] raw) {
        InstalledGeoModel model = InstalledGeoCompiler.compile(raw, SYNTHETIC_CONTRACT);
        Quad first = model.quads().getFirst();
        return new MeshSnapshot(
                model.quads().size(),
                snapshot(first.first()),
                snapshot(first.second()),
                snapshot(first.third()),
                snapshot(first.fourth())
        );
    }

    private static VertexSnapshot snapshot(Vertex vertex) {
        return new VertexSnapshot(
                vertex.position().x(),
                vertex.position().y(),
                vertex.position().z(),
                vertex.u(),
                vertex.v()
        );
    }
}
