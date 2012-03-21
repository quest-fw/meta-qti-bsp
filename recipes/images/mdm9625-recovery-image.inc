# The mkbootimg production process.
require mdm-recovery-bootimg.inc

# The OE-Core minimal boot image files.
IMAGE_INSTALL += task-core-boot
IMAGE_INSTALL += ${ROOTFS_PKGMANAGE_BOOTSTRAP}
IMAGE_INSTALL += ${POKY_EXTRA_INSTALL}

# The MSM Linux minimal boot image files.
IMAGE_INSTALL += "base-files"
IMAGE_INSTALL += "base-passwd"
IMAGE_INSTALL += "system-core-adbd"
IMAGE_INSTALL += "system-core-usb"
IMAGE_INSTALL += "recovery"
IMAGE_INSTALL += "powerapp"
IMAGE_INSTALL += "powerapp-powerconfig"
IMAGE_INSTALL += "powerapp-reboot"
IMAGE_INSTALL += "powerapp-shutdown"