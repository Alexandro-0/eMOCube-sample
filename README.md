# eMOCube-sample
本 repository 是使用藍芽相關功能的範例

本範例只使用兩個 activity
- MainActivity
- ControlActivity

這兩個 activity 只會注重介面邏輯
藍芽相關的邏輯已由 ScanActivityTemplate 與 ControlActivityTemplate 實作
故 MainActivity 與 ControlActivity 只需要繼承此二abstract class即可

因此，本範例的意義在於：不必特別處理藍芽使用流程與邏輯，專注處理介面邏輯即可。
