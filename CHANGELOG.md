# Changelog

## 0.1.0-alpha.4 - 2026-08-31

- Preserve the alpha.3 renderer and BlueMap 5.23 adapter bytes.
- Seal Gradle module metadata with the exact Gradle 9.6.1 release toolchain.
- Supersede the unpublished alpha.3 tag, whose workflow stopped before
  creating release assets or a Maven package.

## 0.1.0-alpha.3 - 2026-08-31

- Target only BlueMap feature-backport commit
  `7e07f4e74ec1e92a6ead9aa1e66054af3e133aac` and API commit
  `285c9a60eff3ac2b0cab308ce1058d1565be0971`.
- Move the local adapter boundary from `bluemap522` to `bluemap523`.
- Compile the four shared Adapter API bootstrap helpers and remove the three
  duplicate local helpers.
- Keep the exact profile, ten installed-GEO contracts, 12-case gallery,
  renderer behavior, and stock fallback unchanged.

## 0.1.0-alpha.2 - 2026-08-30

- Replaced the repository-local installed-GEO compiler and model with the
  released first-party source module while keeping all ten Ars Nouveau
  contracts, resource admission, routes, emission, and fallback local.

## 0.1.0-alpha.1 - 2026-08-27

- Generated a fail-closed Java 21 BlueMap add-on seed for `ars-nouveau-5.13.0-mc1.21.1`.
- Restored deterministic installed GEO meshes and textures for the Enchanting
  Apparatus, Imbuement Chamber, five source relays, and four spell turrets.
- Added strict legal-state routing, client-matching turret orientation, bounded
  route diagnostics, exact artifact admission, focused tests, and a 12-case
  comparison gallery.
