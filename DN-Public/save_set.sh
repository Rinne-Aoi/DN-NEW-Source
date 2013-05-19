#!/system/bin/sh
#==============================================================================
# DreamNarae Save
# Version 4.0
# Made by Ayana(riina_01@naver.com)
# Edit by Sopiane(http://www.sirospace.com)
# Colorful Harmony Team- Angeloid Team
# http://www.angeloiddev.com                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
# DO NOT EDIT THIS SCRIPT!
#==============================================================================
setprop dalvik.vm.execution-mode int:fast
setprop debug.composition.type dyn
setprop enable.frequency.save 10
setprop enable.NW:NW.operate Hand-over
setprop enable.sf.cache.type memory
setprop enale.nextAction.reckon earlier
setprop persist.sys.ui.hw 1
setprop persist.sys.use_dithering 0
setprop persist.text.type clear
setprop pm.sleep_mode 1
setprop proximity_incall enable
setprop ro.battery,hw.run.mode parallel
setprop ro.battery.voltage.cooperate true
setprop ro.cache.memoery.cooperate true
setprop ro.config.cpuui.rendering false
setprop ro.config.hw_power_saving 1
setprop ro.config.hw_quickpoweron true
setprop ro.connect.action slippery
setprop ro.dev.dmm.dpd.start_address 0
setprop ro.ext4fs 1
setprop ro.HOME_APP_ADJ 1
setprop ro.hwui.render_dirty_region false
setprop ro.kernel.android.checkjni 0
setprop ro.mot.dalvik.warnonly true
setprop ro.min_pointer_dur 10
setprop ro.product.process.RAM.save 2
setprop ro.product.sametime.delay false
setprop ro.product.use_charge_counter 1
setprop ro.ril.disable.power.collapse 0
setprop ro.ril.sensor.sleep.control 1
setprop ro.screen.rendering false
setprop ro.sf.cache.type memory
setprop ro.sf.compbypass.enable 1
setprop ro.sf.data.suspend.type n
setprop ro.voltage.cycle 1
setprop windowsmgr.max_events_per_sec 300
setprop wifi.supplicant_scan_interval 240

if [ -e /proc/sys/vm/swappiness ]; then
    echo 50 > /proc/sys/vm/swappiness;
	echo 50 > /proc/sys/vm/swappiness
	echo "Activited!"
fi

if [ -e /proc/sys/vm/vfs_cache_pressure ]; then
    echo 60 > /proc/sys/vm/vfs_cache_pressure;
    echo 60 > /proc/sys/vm/vfs_cache_pressure
	echo "Activited!"
fi

if [ -e /proc/sys/vm/min_free_kbytes ]; then
    echo 2048 > /proc/sys/vm/min_free_kbytes;
	echo 2048 > /proc/sys/vm/min_free_kbytes
	echo "Activited!"
fi

if [ -e /proc/sys/vm/dirty_expire_centisecs ]; then
    echo 3072 > /proc/sys/vm/dirty_expire_centisecs;
	echo 3072 > /proc/sys/vm/dirty_expire_centisecs
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/cpu0/cpufreq/deepsleep_cpulevel ]; then
    echo "4" > /sys/devices/system/cpu/cpu0/cpufreq/deepsleep_cpulevel
    echo "4" > /sys/devices/system/cpu/cpu0/cpufreq/deepsleep_cpulevel;
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/cpu0/cpufreq/deepsleep_buslevel ]; then
    echo "1" > /sys/devices/system/cpu/cpu0/cpufreq/deepsleep_buslevel
    echo "1" > /sys/devices/system/cpu/cpu0/cpufreq/deepsleep_buslevel;
	echo "Activited!"
fi

if [ -e /sys/module/cpuidle/parameters/enable_mask ]; then
    echo "3" > /sys/module/cpuidle/parameters/enable_mask
    echo "3" > /sys/module/cpuidle/parameters/enable_mask;
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/sched_mc_power_savings ]; then
    echo "1" > /sys/devices/system/cpu/sched_mc_power_savings
    echo "1" > /sys/devices/system/cpu/sched_mc_power_savings;
	echo "Activited!"
fi

echo "DreamNarae Save"
