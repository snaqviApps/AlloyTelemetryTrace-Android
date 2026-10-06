# Reference Shell Architecture

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


<b>START RECORDING (15m)</b>:     Data-Acquisition and saving to memory; turns off automatically after 15 minutes.</br>
<b>START LIVE INSPECTOR</b>:      Starts Data-Acquisition from A/V sensors with Analysis (5 minutes limit).</br>
<b>ANALYZE N DISPLAY:</b>        'Offline' mode for retrieving and displaying pre-recorded data.</br>

 -----------------
- Tasks done:
- moved architecture to 04 modules
  - core:domain
  - core:data
  - feature:placeholder:data
  - feature:placeholder:presentation ----> it has currently the Placehoder data (movies-List)

- refactoring: 
  - moved endPoint @PlaceHoderViewModel.kt to MutableStateFlow() to decouple from Compose-runtime library
### Operational Modes:
