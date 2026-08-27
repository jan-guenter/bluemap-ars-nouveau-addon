#!/usr/bin/env python3
# SPDX-License-Identifier: MIT
"""Lint the generated Ars Nouveau visual gallery without Minecraft."""

from __future__ import annotations

import json
from pathlib import Path
import re
import sys

sys.dont_write_bytecode = True
import cases
import generate


ROOT = Path(__file__).resolve().parent
EXPECTED_BLOCK_STATES = {
    "ars_nouveau:enchanting_apparatus[facing=up]",
    "ars_nouveau:imbuement_chamber[facing=up]",
    "ars_nouveau:relay",
    "ars_nouveau:relay_splitter",
    "ars_nouveau:relay_deposit",
    "ars_nouveau:relay_warp",
    "ars_nouveau:relay_collector",
    "ars_nouveau:basic_spell_turret",
    "ars_nouveau:rotating_spell_turret",
    "ars_nouveau:spell_turret",
    "ars_nouveau:timer_spell_turret",
    "minecraft:stone",
}


def inside_envelope(x: int, y: int, z: int) -> bool:
    minimum_x, minimum_y, minimum_z, maximum_x, maximum_y, maximum_z = (
        cases.ENVELOPE
    )
    return (
        minimum_x <= x <= maximum_x
        and minimum_y <= y <= maximum_y
        and minimum_z <= z <= maximum_z
    )


def main() -> int:
    for relative, payload in generate.generated_files().items():
        path = ROOT / relative
        if not path.is_file() or path.read_bytes() != payload:
            raise ValueError(f"generated file differs: {relative}")

    json.loads((ROOT / "datapack/pack.mcmeta").read_text(encoding="utf-8"))
    load_tag = json.loads(
        (ROOT / "datapack/data/minecraft/tags/function/load.json").read_text(
            encoding="utf-8"
        )
    )
    if load_tag != {"values": [f"{cases.NAMESPACE}:load"]}:
        raise ValueError("load tag differs from the exact namespace")

    case_ids = tuple(placement.case_id for placement in cases.PLACEMENTS)
    if len(case_ids) != 12 or len(set(case_ids)) != len(case_ids):
        raise ValueError("gallery must contain 12 uniquely named cases")
    block_states = {placement.block_state for placement in cases.PLACEMENTS}
    if block_states != EXPECTED_BLOCK_STATES:
        raise ValueError("gallery differs from the exact 11-block scope")
    if sum(state.startswith("ars_nouveau:") for state in block_states) != 11:
        raise ValueError("gallery must contain exactly 11 Ars Nouveau blocks")
    if tuple(placement.block_state for placement in cases.PLACEMENTS).count(
        "minecraft:stone"
    ) != 1:
        raise ValueError("gallery must contain exactly one stone stock control")

    if len(cases.LABELS) != len(cases.PLACEMENTS):
        raise ValueError("every visual case needs one coordinate sign")
    placement_positions = {
        (placement.x, placement.y, placement.z) for placement in cases.PLACEMENTS
    }
    label_targets = {
        (label.target_x, label.target_y, label.target_z) for label in cases.LABELS
    }
    if label_targets != placement_positions:
        raise ValueError("coordinate signs must cover every visual case")
    if any(
        len(line) > 15
        for label in cases.LABELS
        for line in (label.line_1, label.line_2)
    ):
        raise ValueError("sign label line is too long to read")
    for placement in cases.PLACEMENTS:
        if not inside_envelope(placement.x, placement.y, placement.z):
            raise ValueError(f"case escaped the envelope: {placement.case_id}")
    for label in cases.LABELS:
        if not inside_envelope(label.x, label.y, label.z):
            raise ValueError("coordinate sign escaped the bounded envelope")

    minimum_x, minimum_y, minimum_z, maximum_x, maximum_y, maximum_z = (
        cases.ENVELOPE
    )
    volume = (
        (maximum_x - minimum_x + 1)
        * (maximum_y - minimum_y + 1)
        * (maximum_z - minimum_z + 1)
    )
    if volume > 32_768:
        raise ValueError("gallery clear command exceeds the fill limit")

    function_root = ROOT / f"datapack/data/{cases.NAMESPACE}/function"
    functions = "\n".join(
        path.read_text(encoding="utf-8")
        for path in sorted(function_root.glob("*.mcfunction"))
    )
    ars_blocks = re.findall(
        r"^setblock .* ars_nouveau:[a-z0-9_]+(?:\[[^]]+\])?$",
        functions,
        re.MULTILINE,
    )
    if len(ars_blocks) != 11:
        raise ValueError("build function must place exactly 11 Ars Nouveau blocks")
    signs = re.findall(
        r"^setblock .* minecraft:oak_sign\[rotation=8,waterlogged=false\]",
        functions,
        re.MULTILINE,
    )
    if len(signs) != 12:
        raise ValueError("build function must place exactly 12 coordinate signs")
    lowered = functions.lower()
    for forbidden in ("summon ", "data merge", "op ", "deop ", "stop "):
        if forbidden in lowered:
            raise ValueError(f"forbidden gallery command: {forbidden}")
    print("Ars Nouveau gallery lint passed: 11 routed blocks and one stock control")
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError) as error:
        print(f"gallery lint failed: {error}", file=sys.stderr)
        raise SystemExit(1)
