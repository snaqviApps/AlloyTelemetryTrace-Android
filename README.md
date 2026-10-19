# Body Habits Empirical Analysis Presentation Tool

This App is a a tool to revisit an individual habits, currently planned, snorting data patterns presentation. 
Tech-base: Uses clean architecture, Compose-UI tookit: Scaffold-provided, topBar, baseBar, and content blocks. 

[branch: dasbhboard-system-health-check-telemetry]
This branch implements System-level health check or Pre-Flight Readiness matrix, providing Telemetry data,
currently planned as:

---

### SYSTEM PRE-FLIGHT READINESS

| | |
| :---| :--- |
| `[✓] 5G Capable` | `[✓] Wi-Fi Active` |
| `[✓] Battery > 50%` | `[✓] Storage OK` |

---

### LIVE SIGNAL STRENGTH (RSRP)

`[ ████████░░░░░░░ ] -85 dBm (Good)`  
`\  _/\_`  
`   \/    \`  

---

* `[ START RECORDING (15m) ]`
* `[ START LIVE INSPECTOR (5m) ]`
* `[ ANALYZE N DISPLAY (OFFLINE) ]`

---

### Operational Modes:
* **START RECORDING (15m):** Data-Acquisition and saving to memory; turns off automatically after 15 minutes.
* **START LIVE INSPECTOR:** Starts Data-Acquisition from A/V sensors with Analysis (5 minutes limit).
* **ANALYZE N DISPLAY:** 'Offline' mode for retrieving and displaying pre-recorded data.

---

### Tasks done:
- moved architecture to 04 modules
  - `core:domain`
  - `core:data`
  - `feature:placeholder:data`
  - `feature:placeholder:presentation` ----> Placeholder data (movies-List)

- refactoring: 
  - moved endPoint @PlaceHoderViewModel.kt to `MutableStateFlow()` to decouple from Compose-runtime library

  <table>
  <!-- Row 1: Titles -->
  <tr>
    <td align="center" valign="bottom">
      <b>Pre-Flight Health Telemetry Status </b>
    </td>
  </tr>
  <!-- Row 2: Images -->
  <tr>
    <td valign="top">
     <img width="1080" height="2400" alt="Screenshot_20261019_001649" src="https://github.com/user-attachments/assets/0232db17-fa01-482d-98ed-8b75e419e2c9" />
    </td>
  </tr>
  </table>
