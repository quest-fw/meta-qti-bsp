#!/bin/sh
# Copyright (c) 2014, The Linux Foundation. All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are
# met:
#     * Redistributions of source code must retain the above copyright
#       notice, this list of conditions and the following disclaimer.
#     * Redistributions in binary form must reproduce the above
#       copyright notice, this list of conditions and the following
#       disclaimer in the documentation and/or other materials provided
#       with the distribution.
#     * Neither the name of The Linux Foundation nor the names of its
#       contributors may be used to endorse or promote products derived
#       from this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
# WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
# MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT
# ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
# BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
# CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
# SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
# BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
# WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
# OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
# IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#
# find_partitions        init.d script to dynamically find partitions
#

FindAndMountUBI () {
   partition=$1
   dir=$2

   mtd_block_number=`cat $mtd_file | grep -i $partition | sed 's/^mtd//' | awk -F ':' '{print $1}'`
   echo "MTD : Detected block device : $dir for $partition"
   mkdir -p $dir
   ubiattach -m $mtd_block_number -d $ubi_device_number /dev/ubi_ctrl
   mount -t ubifs /dev/ubi"$ubi_device_number"_0 $dir -o bulk_read
   echo "MTD : Mounting of /dev/ubi"$ubi_device_number"_0 on $dir done"

   ubi_device_number=$((ubi_device_number+1))
}

FindAndMountEXT4 () {
   partition=$1
   dir=$2

   mmc_block_device=`cat $emmc_file | grep -i $partition | awk -F ':' '{print $1}'`
   echo "EMMC : Detected block device : $dir for $partition"
   mkdir -p $dir
   mount -t ext4 /dev/$mmc_block_device $dir
   echo "EMMC : Mounting of /dev/$mmc_block_device on $dir done"
}

mtd_file="/proc/mtd"
emmc_file="/proc/emmc"

if [ -f "$mtd_file" ]
then
    fstype="UBI"
    ubi_device_number=1
elif [ -f "$emmc_file" ]
then
    fstype="EXT4"
fi

eval FindAndMount${fstype} userdata /usr
eval FindAndMount${fstype} cache /cache
eval FindAndMount${fstype} modem /firmware

exit 0
