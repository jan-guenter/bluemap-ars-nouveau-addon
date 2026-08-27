#!/usr/bin/env python3
# SPDX-License-Identifier: MIT
"""Initial Ars Nouveau Geo-rendered block comparison cases."""

from __future__ import annotations

from dataclasses import dataclass


NAMESPACE = "ars_nouveau_gallery"
ENVELOPE = (172, 99, 172, 204, 104, 204)
TELEPORT = (188, 112, 188, 0, 40)


@dataclass(frozen=True)
class Placement:
    case_id: str
    label: str
    x: int
    y: int
    z: int
    block_state: str
    expected: str


@dataclass(frozen=True)
class Label:
    x: int
    y: int
    z: int
    line_1: str
    line_2: str
    target_x: int
    target_y: int
    target_z: int


def at(
    block_name: str,
    label: str,
    x: int,
    z: int,
    *,
    state: str = "",
) -> Placement:
    suffix = f"[{state}]" if state else ""
    return Placement(
        block_name.replace("_", "-"),
        label,
        x,
        101,
        z,
        f"ars_nouveau:{block_name}{suffix}",
        "installed-geo-static-visible",
    )


PLACEMENTS = (
    at(
        "enchanting_apparatus",
        "Enchanting Apparatus facing up",
        176,
        176,
        state="facing=up",
    ),
    at(
        "imbuement_chamber",
        "Imbuement Chamber facing up",
        184,
        176,
        state="facing=up",
    ),
    at("relay", "Source Relay", 176, 184),
    at("relay_splitter", "Relay Splitter", 182, 184),
    at("relay_deposit", "Relay Deposit", 188, 184),
    at("relay_warp", "Warp Relay", 194, 184),
    at("relay_collector", "Relay Collector", 200, 184),
    at("basic_spell_turret", "Basic Spell Turret", 176, 192),
    at("rotating_spell_turret", "Rotating Spell Turret", 184, 192),
    at("spell_turret", "Spell Turret", 192, 192),
    at("timer_spell_turret", "Timer Spell Turret", 200, 192),
    Placement(
        "stock-control",
        "stone stock rendering control",
        200,
        101,
        200,
        "minecraft:stone",
        "stock-visible",
    ),
)


LABELS = (
    Label(176, 101, 178, "ENCHANTING", "APPARATUS", 176, 101, 176),
    Label(184, 101, 178, "IMBUEMENT", "CHAMBER", 184, 101, 176),
    Label(176, 101, 186, "SOURCE", "RELAY", 176, 101, 184),
    Label(182, 101, 186, "RELAY", "SPLITTER", 182, 101, 184),
    Label(188, 101, 186, "RELAY", "DEPOSIT", 188, 101, 184),
    Label(194, 101, 186, "WARP", "RELAY", 194, 101, 184),
    Label(200, 101, 186, "RELAY", "COLLECTOR", 200, 101, 184),
    Label(176, 101, 194, "BASIC SPELL", "TURRET", 176, 101, 192),
    Label(184, 101, 194, "ROTATING", "TURRET", 184, 101, 192),
    Label(192, 101, 194, "SPELL", "TURRET", 192, 101, 192),
    Label(200, 101, 194, "TIMER SPELL", "TURRET", 200, 101, 192),
    Label(200, 101, 202, "STOCK", "STONE", 200, 101, 200),
)
