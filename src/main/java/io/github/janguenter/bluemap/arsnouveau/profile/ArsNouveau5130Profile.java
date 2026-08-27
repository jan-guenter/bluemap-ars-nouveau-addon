/*
 * SPDX-License-Identifier: MIT
 */

package io.github.janguenter.bluemap.arsnouveau.profile;

import java.util.List;

/** Exact All the Mons 1.2.0 profile `ars-nouveau-5.13.0-mc1.21.1`. */
public final class ArsNouveau5130Profile {

    public static final String PROFILE_ID = "ars-nouveau-5.13.0-mc1.21.1";
    public static final ArtifactPin ARS_NOUVEAU = new ArtifactPin(
            "arsNouveau",
            "ars_nouveau",
            "5.13.0",
            "ars_nouveau-1.21.1-5.13.0.jar",
            20_096_005L,
            "90796df69bfb39b1a9c79edbfa01c2425e5b86aea47dc55ebdcbf30e88f47592"
    );
    public static final List<ArtifactPin> ARTIFACTS = List.of(ARS_NOUVEAU);
    public static final List<ResourcePin> RESOURCES = List.of(
            resource("geo/enchanting_apparatus.geo.json", 10_249,
                    "abd1209769340b411c0cd9e80e9d1d6fdfa8d3dffa5025f3c3e4946bd1267e22"),
            resource("geo/imbuement_chamber.geo.json", 9_035,
                    "df6266180f8dd9104960a2153ff1b6063f3bf03ff8d4a1cd68c26ef9fb958a3b"),
            resource("geo/source_relay.geo.json", 8_288,
                    "ba87df3739427158ff52daa550218ec24f15b1eae1c03c3accb878b58089d608"),
            resource("geo/source_splitter.geo.json", 6_422,
                    "6f1cdbc746bb390e39dda2b9b560ae828c676314a212b70ce9a1a9de73ad09d9"),
            resource("geo/source_deposit.geo.json", 6_174,
                    "cf5c3b375c931de6b4ef316d88ff3030926c3fcc4c7dec9cec97876249e656f9"),
            resource("geo/source_warp.geo.json", 6_444,
                    "0bbfdfdadac4b40ae255bdceb9fd771527ed288b2c45cc21ceb332177f2e4647"),
            resource("geo/source_collector.geo.json", 6_205,
                    "84ff6a9e005a7b726c59bb3abbcf7947e20c40487ab27b4ed3ed47522564cc21"),
            resource("geo/basic_spell_turret.geo.json", 6_782,
                    "32fec71ebeb2690c59c7128f843348f76486418dac74376f05074a745924ac0d"),
            resource("geo/spell_turret.geo.json", 8_154,
                    "35dfc5d0eb4143fc8c767cbf9fa2f7e8d451c413215f773851b7a97fc76c1f29"),
            resource("geo/spell_turret_timer.geo.json", 7_328,
                    "e18600d4f74a34a3589c01f99dfdda5e9ebdfff1074396a4d56938db57e49191"),
            texture("enchanting_apparatus.png", 286,
                    "903413bafb52bd614aa3fad29d4534918de9f1a688f6cf5785be8e27d66be819"),
            texture("imbuement_chamber.png", 454,
                    "2576e4c9e8b578da1cd459223234be513413ea03aeb7470d3969cdeb64994f59"),
            texture("source_relay.png", 392,
                    "0befa6d3def903cd8f70edcb1da8f07c38dc1b5eb665310d274799834da3ff4b"),
            texture("source_splitter.png", 322,
                    "a6c066807b284e1da3590b4661186938699aef13e2e41cd3e2b26d77a9bb7c9e"),
            texture("source_deposit.png", 371,
                    "cf55e9e6460e649b8decb3840fa94f68f37093175cbdc5545284d66f2bc9f58e"),
            texture("source_warp.png", 328,
                    "d058204281671191387a7cdf920579dabb8bc28d606802fbeb5d3aaa675efa87"),
            texture("source_collector.png", 379,
                    "ea20cecf723e95dad8f281963cc351dc38543d3894dd9567c46e5e24510a9433"),
            texture("basic_spell_turret.png", 323,
                    "718f6ab749b668b499514c3e070ee1b97011e810627f428b855c8e1790ed73f9"),
            texture("spell_turret.png", 426,
                    "f24ffcb350e248bacbfd72b774434bd824f714d1bcfda7ee8086837b33285781"),
            texture("spell_turret_timer.png", 365,
                    "f2c61756f1aca9be2e75ad62b71597180c9b004f047aff0fd508ea275234cb79")
    );

    private ArsNouveau5130Profile() {
    }

    private static ResourcePin resource(String path, int size, String sha256) {
        return new ResourcePin(
                ARS_NOUVEAU, "assets/ars_nouveau/" + path, size, sha256
        );
    }

    private static ResourcePin texture(String name, int size, String sha256) {
        return resource("textures/block/" + name, size, sha256);
    }

    /** Exact installed resource admitted only with its containing artifact. */
    public record ResourcePin(
            ArtifactPin artifact,
            String path,
            int size,
            String sha256
    ) {
    }
}
