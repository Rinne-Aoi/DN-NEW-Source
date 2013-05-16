#!/system/bin/sh
#==============================================================================
# DreamNarae Prev
# Version 2.0
# Made by Eun-hee, Lee(rie6752@naver.com)
# Edit by Sopiane(http://www.sirospace.com)
# Colorful Harmony Team- Angeloid Team
# http://www.sirospace.info
# DO NOT EDIT THIS SCRIPT!
#==============================================================================
setprop dalvik.vm.execution-mode int:jit
setprop debug.composition.type cpu
setprop debug.composition.type-mode parallel
setprop debug.egl.hw 1
setprop debug.egl.profiler 1
setprop debug.performance.tuning 1
setprop debug.ram.applicationforce 1
setprop debug.sf.enable_hgl 1
setprop debug.sf.hw 1
setprop enable.frequency.save 10
setprop enable.NW:NW.operate Hand-over
setprop enable.Ram,Memory.run.parallel 1
setprop enable.sf.cache.type memory
setprop enable.work.run.mainboard power
setprop enale.nextAction.reckon earlier
setprop persist.cache.operate quickdrop
setprop persist.sys.shutdown.mode hibernate
setprop persist.sys.ui.hw 1
setprop persist.sys.use_dithering 0
setprop persist.text.type clear
setprop pm.sleep_mode 1
setprop ro.A/D,D/A.translation true
setprop ro.battery,hw.run.mode dispersion
setprop ro.battery.voltage.cooperate true
setprop ro.cache.memoery.cooperate true
setprop ro.config.cpu.handling.delay few
setprop ro.config.cpuui.rendering false
setprop ro.config.hardware.run accelerate
setprop ro.config.hw_quickpoweron true
setprop ro.connect.action slippery
setprop ro.dev.dmm.dpd.start_address 0
setprop ro.ext4fs 1
setprop ro.HOME_APP_ADJ 1
setprop ro.hwui.render_dirty_region false
setprop ro.kernel.android.checkjni 0
setprop ro.media.enc.hprof.vid.fps 75
setprop ro.media.enc.hprof.vid.fps 80
setprop ro.mot.dalvik.warnonly true
setprop ro.min_pointer_dur 10
setprop ro.product.process.RAM.save 2
setprop ro.product.sametime.delay false
setprop ro.product.use_charge_counter 1
setprop ro.ril.spender.bending 1
setprop ro.ril.disable.power.collapse 0
setprop ro.screen.rendering false
setprop ro.sf.cache.type memory
setprop ro.sf.compbypass.enable 1
setprop ro.sf.data.load.type hw
setprop ro.sf.data.suspend.type n
setprop ro.sys.compute stability
setprop ro.sys.print.power hw
setprop ro.sys.throughput rapidly
setprop ro.vga.force 1
setprop ro.voltage.cycle 1
setprop video.accelerate.hw 1
setprop windowsmgr.max_events_per_sec 300
setprop wifi.supplicant_scan_interval 180
if [ -e /proc/sys/vm/swappiness ]; then
    echo 50 > /proc/sys/vm/swappiness;
	echo 50 > /proc/sys/vm/swappiness
	echo "Activited!"
fi

if [ -e /proc/sys/vm/min_free_kbytes ]; then
    echo 3072 > /proc/sys/vm/min_free_kbytes;
	echo 3072 > /proc/sys/vm/min_free_kbytes
	echo "Activited!"
fi

if [ -e /proc/sys/vm/dirty_expire_centisecs ]; then
    echo 3072 > /proc/sys/vm/dirty_expire_centisecs;
	echo 3072 > /proc/sys/vm/dirty_expire_centisecs
	echo "Activited!"
fi

if [ -e /proc/sys/vm/dirty_expire_centisecs ]; then
    echo 3072 > /proc/sys/vm/dirty_expire_centisecs;
	echo 3072 > /proc/sys/vm/dirty_expire_centisecs
	echo "Activited!"
fi

if [ -e /proc/sys/vm/dirty_writeback_centisecs ]; then
    echo 1500 > /proc/sys/vm/dirty_writeback_centisecs;
	echo 1500 > /proc/sys/vm/dirty_writeback_centisecs
	echo "Activited!"
fi

if [ -e /proc/sys/vm/dirty_background_ratio ]; then
    echo 7 > /proc/sys/vm/dirty_background_ratio;
	echo 7 > /proc/sys/vm/dirty_background_ratio
	echo "Activited!"
fi

if [ -e /proc/sys/vm/dirty_ratio ]; then
    echo 30 > /proc/sys/vm/dirty_ratio;
	echo 30 > /proc/sys/vm/dirty_ratio
	echo "Activited!"
fi

if [ -e /proc/sys/vm/vfs_cache_pressure ]; then
    echo 50 > /proc/sys/vm/vfs_cache_pressure;
    echo 50 > /proc/sys/vm/vfs_cache_pressure
	echo "Activited!"
fi

if [ -e /proc/sys/vm/laptop_mode ]; then
    echo 0 > /proc/sys/vm/laptop_mode;
    echo 0 > /proc/sys/vm/laptop_mode
	echo "Activited!"
fi

if [ -e /proc/sys/vm/panic_on_oom ]; then
    echo 0 > /proc/sys/vm/panic_on_oom;
    echo 0 > /proc/sys/vm/panic_on_oom
	echo "Activited!"
fi

if [ -e /proc/sys/vm/page-cluster ]; then
    echo 3 > /proc/sys/vm/page-cluster;
    echo 3 > /proc/sys/vm/page-cluster
	echo "Activited!"
fi

if [ -e /proc/sys/kernel/sched_min_granularity_ns ]; then
    echo "1500000" > /proc/sys/kernel/sched_min_granularity_ns
    echo "1500000" > /proc/sys/kernel/sched_min_granularity_ns;
	echo "Activited!"
fi

if [ -e /proc/sys/kernel/sched_latency_ns ]; then
    echo "18000000" > /proc/sys/kernel/sched_latency_ns
    echo "18000000" > /proc/sys/kernel/sched_latency_ns;
	echo "Activited!"
fi

if [ -e /proc/sys/kernel/sched_wakeup_granularity_ns ]; then
    echo "3000000" > /proc/sys/kernel/sched_wakeup_granularity_ns
    echo "3000000" > /proc/sys/kernel/sched_wakeup_granularity_ns;
	echo "Activited!"
fi
if [ -e /sys/devices/system/cpu/cpu0/cpufreq/deepsleep_cpulevel ]; then
    echo "5" > /sys/devices/system/cpu/cpu0/cpufreq/deepsleep_cpulevel
    echo "5" > /sys/devices/system/cpu/cpu0/cpufreq/deepsleep_cpulevel;
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/cpu0/cpufreq/deepsleep_buslevel ]; then
    echo "2" > /sys/devices/system/cpu/cpu0/cpufreq/deepsleep_buslevel
    echo "2" > /sys/devices/system/cpu/cpu0/cpufreq/deepsleep_buslevel;
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

if [ -e /sys/devices/system/cpu/cpu0/cpufreq/smooth_target ]; then
	echo "2" > /sys/devices/system/cpu/cpu0/cpufreq/smooth_target
	echo "2" > /sys/devices/system/cpu/cpu0/cpufreq/smooth_target;
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/cpu1/cpufreq/smooth_target ]; then
	echo "2" > /sys/devices/system/cpu/cpu1/cpufreq/smooth_target
	echo "2" > /sys/devices/system/cpu/cpu1/cpufreq/smooth_target;
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/cpu2/cpufreq/smooth_target ]; then
	echo "2" > /sys/devices/system/cpu/cpu2/cpufreq/smooth_target
	echo "2" > /sys/devices/system/cpu/cpu2/cpufreq/smooth_target;
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/cpu3/cpufreq/smooth_target ]; then
	echo "2" > /sys/devices/system/cpu/cpu3/cpufreq/smooth_target
	echo "2" > /sys/devices/system/cpu/cpu3/cpufreq/smooth_target;
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/cpu0/cpufreq/smooth_offset ]; then
	echo "1" > /sys/devices/system/cpu/cpu0/cpufreq/smooth_offset
	echo "1" > /sys/devices/system/cpu/cpu0/cpufreq/smooth_offset;
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/cpu1/cpufreq/smooth_offset ]; then
	echo "1" > /sys/devices/system/cpu/cpu1/cpufreq/smooth_offset
	echo "1" > /sys/devices/system/cpu/cpu1/cpufreq/smooth_offset;
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/cpu2/cpufreq/smooth_offset ]; then
	echo "1" > /sys/devices/system/cpu/cpu2/cpufreq/smooth_offset
	echo "1" > /sys/devices/system/cpu/cpu2/cpufreq/smooth_offset;
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/cpu3/cpufreq/smooth_offset ]; then
	echo "1" > /sys/devices/system/cpu/cpu3/cpufreq/smooth_offset
	echo "1" > /sys/devices/system/cpu/cpu3/cpufreq/smooth_offset;
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/cpu0/cpufreq/smooth_step ]; then
	echo "1" > /sys/devices/system/cpu/cpu0/cpufreq/smooth_step
	echo "1" > /sys/devices/system/cpu/cpu0/cpufreq/smooth_step;
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/cpu1/cpufreq/smooth_step ]; then
	echo "1" > /sys/devices/system/cpu/cpu1/cpufreq/smooth_step
	echo "1" > /sys/devices/system/cpu/cpu1/cpufreq/smooth_step;
	echo "Activited!"
fi

if [ -e /sys/devices/system/cpu/cpu2/cpufreq/smooth_step ]; then
	echo "1" > /sys/devices/system/cpu/cpu2/cpufreq/smooth_step
	echo "1" > /sys/devices/system/cpu/cpu2/cpufreq/smooth_step;
	echo "Activited!"
fi
if [ -e /sys/devices/system/cpu/cpu3/cpufreq/smooth_step ]; then
	echo "1" > /sys/devices/system/cpu/cpu3/cpufreq/smooth_step
	echo "1" > /sys/devices/system/cpu/cpu3/cpufreq/smooth_step;
	echo "Activited!"
fi

if [ -e /sys/devices/virtual/misc/second_core/hotplug_on ]; then
	echo "on" > /sys/devices/virtual/misc/second_core/hotplug_on
	echo "on" > /sys/devices/virtual/misc/second_core/hotplug_on;
	echo "Activited!"
fi

if [ -e /sys/module/pm_hotplug/parameters/loadl ]; then
	echo 25 > /sys/module/pm_hotplug/parameters/loadl
	echo 25 > /sys/module/pm_hotplug/parameters/loadl;
	echo "Activited!"
fi

if [ -e /sys/module/pm_hotplug/parameters/loadh ]; then
	echo 25 > /sys/module/pm_hotplug/parameters/loadh
	echo 25 > /sys/module/pm_hotplug/parameters/loadh;
	echo "Activited!"
fi

if [ -e /sys/module/pm_hotplug/parameters/loadl_scroff ]; then
	echo 25 > /sys/module/pm_hotplug/parameters/loadl_scroff
	echo 25 > /sys/module/pm_hotplug/parameters/loadl_scroff;
	echo "Activited!"
fi

if [ -e /sys/module/pm_hotplug/parameters/loadh_scroff ]; then
	echo 25 > /sys/module/pm_hotplug/parameters/loadh_scroff
	echo 25 > /sys/module/pm_hotplug/parameters/loadh_scroff;
	echo "Activited!"
fi

if [ -e /sys/module/pm_hotplug/parameters/freq_cpu1on ]; then
	echo 500000 > /sys/module/pm_hotplug/parameters/freq_cpu1on
	echo 500000 > /sys/module/pm_hotplug/parameters/freq_cpu1on;
	echo "Activited!"
fi

if [ -e /sys/module/pm_hotplug/parameters/rate ]; then
	echo 200 > /sys/module/pm_hotplug/parameters/rate
	echo 200 > /sys/module/pm_hotplug/parameters/rate;
	echo "Activited!"
fi

if [ -e /sys/module/pm_hotplug/parameters/rate_cpuon ]; then
	echo 400 > /sys/module/pm_hotplug/parameters/rate_cpuon
	echo 400 > /sys/module/pm_hotplug/parameters/rate_cpuon;
	echo "Activited!"
fi

if [ -e /sys/module/pm_hotplug/parameters/rate_scroff ]; then
	echo 800 > /sys/module/pm_hotplug/parameters/rate_scroff
	echo 800 > /sys/module/pm_hotplug/parameters/rate_scroff;
	echo "Activited!"
fi

echo "DreamNarae Prev"