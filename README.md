This App is a reference-shell for clean architecture, along with Scaffold-provided UI utilities, 
namely: topBar, baseBar, and content blocks, with innerPadding to coordinate due system-bars, 
margins: [status-bar at top, bottom-bar and Camera-pin-hole cut-outs]

[branch: dasbhboard-system-health-check-telemetry]
This branch implements System-level health check or Pre-Flight Readintess matrix, providing Telemetry data,
currently planned as:

┌────────────────────────────────────────┐
│  SYSTEM PRE-FLIGHT READINESS           │
│  [✓] 5G Capable      [✓] Wi-Fi Active  │
│  [✓] Battery > 50%   [✓] Storage OK    │
├────────────────────────────────────────┤
│  LIVE SIGNAL STRENGTH (RSRP)           │
│  [ ████████░░░░░░░ ] -85 dBm (Good)    │
│  \  _/\_                               │
│   \/    \                              │
├────────────────────────────────────────┤
│  [ START RECORDING (15m) ]             │
│  [ START LIVE INSPECTOR (5m) ]         │
│  [ ANALYZE N DISPLAY (OFFLINE) ]       │
└────────────────────────────────────────┘

while above Buttons:
- START RECORDING (15m): Data-Acquisition and saving to memory, it turns Off automatically after 15 minutes
- START LIVE INSPECTOR: Starts Data-Acquisition from A/V sensors with Analysis (5 minutes limit)
- ANALYZE N DISPLAY: This is 'Offline' mode, for retrieving, 
 