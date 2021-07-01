#!/bin/bash

#Nexmon is not supported in > 5.4 kernel
sub_version=$(uname -a |  awk '{split($0,a,"."); print a[2]}')
echo $sub_version
if [ $sub_version -gt 4 ]
then
  #fix Me: figure out better way to get commit HASH.
  sudo rpi-update acdbe016a9622693243b760808b804185f222b73
  sudo reboot
fi

#download kernel header from source
sudo apt install git bc bison flex libssl-dev

sudo wget https://raw.githubusercontent.com/RPi-Distro/rpi-source/master/rpi-source -O /usr/local/bin/rpi-source && sudo chmod +x /usr/local/bin/rpi-source && /usr/local/bin/rpi-source -q --tag-update

rpi-source

#hold kernel and kernel-headers from upgrade
sudo apt-mark hold raspberrypi-kernel
sudo apt-mark hold raspberrypi-kernel-headers
sudo apt update
sudo apt upgrade

#install required packages
sudo apt install raspberrypi-kernel-headers git libgmp3-dev gawk qpdf bison flex make raspberrypi-kernel-headers autoconf texinfo

#clone nexmon git
git clone https://github.com/seemoo-lab/nexmon.git
cd nexmon/

#install gcc and other build pre-requisites
sudo apt install build-essential

#install isl-0.10

cd buildtools/isl-0.10/
./configure
make
sudo make install
ln -s /usr/local/lib/libisl.so /usr/lib/arm-linux-gnueabihf/libisl.so.10

#install mpfr-3.1.4
cd ../mpfr-3.1.4/
./configure
sudo apt install autoconf
sudo apt install automake-1.15
make
sudo make install
sudo ln -s /usr/local/lib/libmpfr.so /usr/lib/arm-linux-gnueabihf/libmpfr.so.4

#build nexmon
cd ../../
source setup_env.sh
make

#bcm43455c0 is for RPI 3B+
#Fix Me: dynamically detect
cd patches/bcm43455c0/7_45_189/

#clone nexmon CSI
git clone --depth=1 https://github.com/seemoo-lab/nexmon_csi.git
cd nexmon_csi
make backup-firmware
make install-firmware

cd utils/makecsiparams
make
#install rule will work only with https://github.com/seemoo-lab/nexmon_csi/pull/227/commits/d254253bc80091bfb167c425b6ba8e939a519680
sudo make install

cd ../../../../../../utilities/nexutil/
make
sudo make install

