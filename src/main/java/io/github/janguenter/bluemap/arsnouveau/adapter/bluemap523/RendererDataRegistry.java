/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arsnouveau.adapter.bluemap523;

import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.util.Key;
import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoModel;

import java.util.IdentityHashMap;
import java.util.Map;

/** Classloader-local compiled data keyed by one BlueMap resource pack. */
final class RendererDataRegistry {

    private static final Map<ResourcePack, Data> DATA = new IdentityHashMap<>();

    private RendererDataRegistry() {
    }

    static synchronized void install(
            ResourcePack pack,
            Map<String, RenderSpec> hosts,
            VariantRendererCatalog variants
    ) {
        DATA.put(pack, new Data(Map.copyOf(hosts), variants));
    }

    static synchronized Data get(ResourcePack pack) {
        return DATA.get(pack);
    }

    record RenderSpec(InstalledGeoModel model, Key texture, StateRoute route) {
    }

    enum StateRoute {
        MOUNTED,
        RELAY,
        TURRET,
        ROTATING_TURRET
    }

    record Data(Map<String, RenderSpec> hosts, VariantRendererCatalog variants) {
    }
}
