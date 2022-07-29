# !/bin/bash

function help
{
    echo
    echo "Usage: sudo $0 <target>"
    echo "Parameters:"
    echo "	<target>"
    echo
    echo "example:"
    echo "sudo $0 raspberrypi"
    exit
}

echo "sudo openbmc.sh $1"
      if [ -z "$1" ]
        then
          echo "check arguments..."
        help
      fi
# 1
# <target>

apt install git python3-distutils gcc g++ make file wget gawk diffstat bzip2 cpio chrpath zstd lz4 bzip2
git clone https://github.com/openbmc/openbmc
cd openbmc
source ./setup
tgt_platform=$1
if [ "$tgt_platform" == "raspberrypi" ]; then
    TEMPLATECONF=meta-evb/meta-evb-raspberrypi/conf . openbmc-env
    #Increase Imaze size: https://github.com/openbmc/openbmc/issues/3590
    #-FLASH_SIZE ?= "32768"
    #+FLASH_SIZE ?= "131072"
    #vi ../meta-phosphor/classes/image_types_phosphor.bbclass
    bitbake obmc-phosphor-image
fi

