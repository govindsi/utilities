#! /bin/bash

help()
{
 echo "sudo spectral_scan.sh -i wlan0 -d ath10k -m mode"
}

while getopts ":i:d:m:h" option; do
  case $option in
    i)
      if=${OPTARG};;
    d)
      driver=${OPTARG};;
    m)
      mode=${OPTARG};;
    h) help
           exit;;
  esac
done

echo $if
phy_if=$(iw $if info | awk '/wiphy/ {print "phy" $2}')
echo $phy_if

if [ "$driver" = "ath10k" ]; then
    #set background scan and trigger spectral scan in FW
    echo background > /sys/kernel/debug/ieee80211/$phy_if/ath10k/spectral_scan_ctl
    echo trigger > /sys/kernel/debug/ieee80211/$phy_if/ath10k/spectral_scan_ctl
    #Initiate scan
    iw dev $if scan
    #disable spectral scan
    echo disable > /sys/kernel/debug/ieee80211/$phy_if/ath10k/spectral_scan_ctl
    #store spectral scan output samples
    cat /sys/kernel/debug/ieee80211/$phy_if/ath10k/spectral_scan0  > /var/log/spectral_samples
elif [ "$driver" = "ath9k" ]; then
    echo chanscan > /sys/kernel/debug/ieee80211/$phy_if/ath9k/spectral_scan_ctl
    iw dev $if scan
    cat /sys/kernel/debug/ieee80211/$phy_if/ath9k/spectral_scan0 > /var/log/spectral_samples
    echo disable > /sys/kernel/debug/ieee80211/$phy_if/ath9k/spectral_scan_ctl
fi
