/* SPDX-License-Identifier: MIT */

package io.github.janguenter.bluemap.arsnouveau.adapter.bluemap522;

import de.bluecolored.bluemap.core.map.hires.block.BlockRendererType;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePack;
import de.bluecolored.bluemap.core.resources.pack.resourcepack.ResourcePackExtension;
import de.bluecolored.bluemap.core.util.Key;
import io.github.janguenter.bluemap.arsnouveau.activation.AddonRuntime;
import io.github.janguenter.bluemap.arsnouveau.adapter.bluemap522.RendererDataRegistry.RenderSpec;
import io.github.janguenter.bluemap.arsnouveau.adapter.bluemap522.RendererDataRegistry.StateRoute;
import io.github.janguenter.bluemap.arsnouveau.profile.ArsNouveau5130Profile;
import io.github.janguenter.bluemap.arsnouveau.profile.ArsNouveau5130Profile.ResourcePin;
import io.github.janguenter.bluemap.arsnouveau.profile.ExactArtifactDetector;
import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoCompiler;
import io.github.janguenter.bluemap.resource.installedgeo.model.InstalledGeoModel;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/** Exact admission, installed GEO compilation, and target-only route installation. */
final class ProfileResourceExtension implements ResourcePackExtension {

    private static final int MAX_ROOTS = 4_096;
    private static final String ASSET_ROOT = "assets/ars_nouveau/";
    private static final Key ENCHANTING_APPARATUS = texture("enchanting_apparatus");
    private static final Key IMBUEMENT_CHAMBER = texture("imbuement_chamber");
    private static final Key SOURCE_RELAY = texture("source_relay");
    private static final Key SOURCE_SPLITTER = texture("source_splitter");
    private static final Key SOURCE_DEPOSIT = texture("source_deposit");
    private static final Key SOURCE_WARP = texture("source_warp");
    private static final Key SOURCE_COLLECTOR = texture("source_collector");
    private static final Key BASIC_SPELL_TURRET = texture("basic_spell_turret");
    private static final Key SPELL_TURRET = texture("spell_turret");
    private static final Key SPELL_TURRET_TIMER = texture("spell_turret_timer");
    private static final Set<Key> TEXTURES = Set.of(
            ENCHANTING_APPARATUS,
            IMBUEMENT_CHAMBER,
            SOURCE_RELAY,
            SOURCE_SPLITTER,
            SOURCE_DEPOSIT,
            SOURCE_WARP,
            SOURCE_COLLECTOR,
            BASIC_SPELL_TURRET,
            SPELL_TURRET,
            SPELL_TURRET_TIMER
    );

    private final ResourcePack resourcePack;
    private final BlockRendererType renderer;
    private final AddonRuntime runtime;
    private Map<String, RenderSpec> hosts;

    ProfileResourceExtension(
            ResourcePack resourcePack,
            BlockRendererType renderer,
            AddonRuntime runtime
    ) {
        this.resourcePack = resourcePack;
        this.renderer = renderer;
        this.runtime = runtime;
    }

    @Override
    public void loadResources(Iterable<Path> roots) {
        hosts = null;
        if (Boolean.getBoolean("bluemap.arsnouveau.disabled")) {
            runtime.inactive("operator-disabled");
            return;
        }
        List<Path> candidates = boundedRoots(roots);
        if (candidates == null || !ExactArtifactDetector.matchesAll(
                candidates, ArsNouveau5130Profile.ARTIFACTS
        )) {
            runtime.inactive("exact-artifact-missing-or-duplicate");
            return;
        }
        Path artifact = ExactArtifactDetector.findExact(
                candidates, ArsNouveau5130Profile.ARS_NOUVEAU
        ).orElse(null);
        if (artifact == null) {
            runtime.inactive("exact-artifact-unavailable");
            return;
        }
        try {
            Map<String, byte[]> resources = new HashMap<>();
            for (ResourcePin pin : ArsNouveau5130Profile.RESOURCES) {
                resources.put(pin.path(), readPinned(artifact, pin));
            }
            hosts = compileHosts(resources);
        } catch (IOException | RuntimeException exception) {
            hosts = null;
            runtime.inactive("resource-compile-" + exception.getClass().getSimpleName());
        }
    }

    @Override
    public Set<Key> collectUsedTextureKeys() {
        return TEXTURES;
    }

    @Override
    public void bake() {
        if (hosts == null) {
            return;
        }
        if (TEXTURES.stream().anyMatch(key -> resourcePack.getTextures().get(key) == null)) {
            runtime.inactive("installed-render-resource-missing");
            return;
        }
        try {
            VariantRendererCatalog variants = VariantRendererCatalog.wrap(
                    resourcePack, renderer
            );
            RendererDataRegistry.install(resourcePack, hosts, variants);
            runtime.activate();
            System.out.println("BlueMap Ars Nouveau add-on active: wrapped "
                    + variants.size() + " installed variants across 11 blocks; "
                    + "ten installed GEO meshes use deterministic static poses.");
        } catch (RuntimeException exception) {
            runtime.inactive("route-install-" + exception.getClass().getSimpleName());
        }
    }

    private static Map<String, RenderSpec> compileHosts(Map<String, byte[]> resources) {
        InstalledGeoModel apparatus = compile(
                resources, "enchanting_apparatus", ArsNouveau5130Profile.ENCHANTING_APPARATUS
        );
        InstalledGeoModel chamber = compile(
                resources, "imbuement_chamber", ArsNouveau5130Profile.IMBUEMENT_CHAMBER
        );
        InstalledGeoModel relay = compile(
                resources, "source_relay", ArsNouveau5130Profile.SOURCE_RELAY
        );
        InstalledGeoModel splitter = compile(
                resources, "source_splitter", ArsNouveau5130Profile.SOURCE_SPLITTER
        );
        InstalledGeoModel deposit = compile(
                resources, "source_deposit", ArsNouveau5130Profile.SOURCE_DEPOSIT
        );
        InstalledGeoModel warp = compile(
                resources, "source_warp", ArsNouveau5130Profile.SOURCE_WARP
        );
        InstalledGeoModel collector = compile(
                resources, "source_collector", ArsNouveau5130Profile.SOURCE_COLLECTOR
        );
        InstalledGeoModel basicTurret = compile(
                resources, "basic_spell_turret", ArsNouveau5130Profile.BASIC_SPELL_TURRET
        );
        InstalledGeoModel spellTurret = compile(
                resources, "spell_turret", ArsNouveau5130Profile.SPELL_TURRET
        );
        InstalledGeoModel timerTurret = compile(
                resources, "spell_turret_timer", ArsNouveau5130Profile.SPELL_TURRET_TIMER
        );
        return Map.ofEntries(
                host("enchanting_apparatus", apparatus, ENCHANTING_APPARATUS,
                        StateRoute.MOUNTED),
                host("imbuement_chamber", chamber, IMBUEMENT_CHAMBER,
                        StateRoute.MOUNTED),
                host("relay", relay, SOURCE_RELAY, StateRoute.RELAY),
                host("relay_splitter", splitter, SOURCE_SPLITTER, StateRoute.RELAY),
                host("relay_deposit", deposit, SOURCE_DEPOSIT, StateRoute.RELAY),
                host("relay_warp", warp, SOURCE_WARP, StateRoute.RELAY),
                host("relay_collector", collector, SOURCE_COLLECTOR, StateRoute.RELAY),
                host("basic_spell_turret", basicTurret, BASIC_SPELL_TURRET,
                        StateRoute.TURRET),
                host("rotating_spell_turret", basicTurret, BASIC_SPELL_TURRET,
                        StateRoute.ROTATING_TURRET),
                host("spell_turret", spellTurret, SPELL_TURRET, StateRoute.TURRET),
                host("timer_spell_turret", timerTurret, SPELL_TURRET_TIMER,
                        StateRoute.TURRET)
        );
    }

    private static InstalledGeoModel compile(
            Map<String, byte[]> resources,
            String name,
            InstalledGeoCompiler.Contract contract
    ) {
        return InstalledGeoCompiler.compile(
                resources.get(ASSET_ROOT + "geo/" + name + ".geo.json"), contract
        );
    }

    private static Map.Entry<String, RenderSpec> host(
            String block,
            InstalledGeoModel model,
            Key texture,
            StateRoute route
    ) {
        return Map.entry(
                "ars_nouveau:" + block, new RenderSpec(model, texture, route)
        );
    }

    private static Key texture(String name) {
        return Key.parse("ars_nouveau:block/" + name);
    }

    private static List<Path> boundedRoots(Iterable<Path> roots) {
        List<Path> result = new ArrayList<>();
        for (Path root : roots) {
            if (Thread.currentThread().isInterrupted() || result.size() >= MAX_ROOTS) {
                return null;
            }
            result.add(root);
        }
        return List.copyOf(result);
    }

    private static byte[] readPinned(Path artifact, ResourcePin pin) throws IOException {
        try (ZipFile zip = new ZipFile(artifact.toFile())) {
            ZipEntry entry = zip.getEntry(pin.path());
            if (entry == null || entry.isDirectory() || entry.getSize() != pin.size()) {
                throw new IOException("installed resource size changed: " + pin.path());
            }
            byte[] raw;
            try (InputStream input = zip.getInputStream(entry)) {
                raw = input.readNBytes(pin.size() + 1);
            }
            if (raw.length != pin.size() || !pin.sha256().equals(digest(raw))) {
                throw new IOException("installed resource bytes changed: " + pin.path());
            }
            return raw;
        }
    }

    private static String digest(byte[] raw) {
        try {
            return HexFormat.of().formatHex(
                    MessageDigest.getInstance("SHA-256").digest(raw)
            );
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 unavailable", exception);
        }
    }
}
