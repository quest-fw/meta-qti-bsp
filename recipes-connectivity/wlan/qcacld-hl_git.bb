inherit autotools-brokensep module

DESCRIPTION = "Qualcomm Atheros WLAN CLD high latency driver"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"

# mdmcalifornium: modulename = wlan_sdio.ko     chip name - qca9377
# others        : modulename = wlan.ko          chip name -
WLAN_MODULE_NAME = "${@base_conditional('BASEMACHINE', 'mdmcalifornium', 'wlan_sdio', 'wlan', d)}"
CHIP_NAME = "${@base_conditional('BASEMACHINE', 'mdmcalifornium', 'qca9377', '', d)}"

FILES_${PN}     += "${base_libdir}/firmware/wlan/*"
FILES_${PN}     += "${base_libdir}/modules/${KERNEL_VERSION}/extra/${WLAN_MODULE_NAME}.ko"
# The inherit of module.bbclass will automatically name module packages with
# kernel-module-" prefix as required by the oe-core build environment. Also it
# replaces '_' with '-' in the module name.
RPROVIDES_${PN} += "${@'kernel-module-${WLAN_MODULE_NAME}'.replace('_', '-')}"

do_unpack[deptask] = "do_populate_sysroot"
PR = "r0-${KERNEL_VERSION}"

#This DEPENDS is to serialize kernel module builds
DEPENDS = "rtsp-alg"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://wlan/qcacld-2.0/"

S = "${WORKDIR}/wlan/qcacld-2.0/"

# Append the chip name to firmware installation path
CHIP_NAME_APPEND = "${@base_conditional('CHIP_NAME', '', '', '/${CHIP_NAME}', d)}"
FIRMWARE_PATH = "${D}${base_libdir}/firmware/wlan/qca_cld${CHIP_NAME_APPEND}"

# Explicitly disable LL to enable HL as current WLAN driver is not having
# simultaneous support of HL and LL.
EXTRA_OEMAKE += "CONFIG_CLD_LL_CORE=n CONFIG_CNSS_PCI=n MODNAME=${WLAN_MODULE_NAME} CHIP_NAME=${CHIP_NAME}"

# The common header file, 'wlan_nlink_common.h' can be installed from other
# qcacld recipes too. To suppress the duplicate detection error, add it to
# SSTATE_DUPWHITELIST.
SSTATE_DUPWHITELIST += "${STAGING_DIR}/${MACHINE}${includedir}/qcacld/wlan_nlink_common.h"

do_install () {
    module_do_install

    install -d ${FIRMWARE_PATH}
    install -m 0644 ${S}/firmware_bin/WCNSS_cfg.dat ${FIRMWARE_PATH}/

    install -d ${D}${includedir}/qcacld/
    install -m 0644 ${S}/CORE/SVC/external/wlan_nlink_common.h ${D}${includedir}/qcacld/
}