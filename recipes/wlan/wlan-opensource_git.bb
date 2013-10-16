DESCRIPTION = "Qualcomm Atheros WLAN Host Driver Module"
SECTION = "kernel/modules"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/BSD;md5=3775480a712fc46a69647678acb234cb"
LICENSE = "BSD"

PR = "r0"

DEPENDS = "virtual/kernel wireless-tools"

SRC_URI = "file://${WORKSPACE}/qcom-opensource/wlan/prima"

S = "${WORKDIR}/prima"

inherit module

EXTRA_OEMAKE += "CONFIG_PRONTO_WLAN=m \
                 KERNEL_BUILD=1"

do_compile () {
    unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC CPP LD
    oe_runmake 'MODPATH="${base_libdir}/modules/wlan/prima"' \
        'KERNEL_SOURCE="${STAGING_KERNEL_DIR}"' \
        'KDIR="${STAGING_KERNEL_DIR}"' \
        'CC="${KERNEL_CC}"' \
        'LD="${KERNEL_LD}"' \
        'WLAN_DIR="${S}"'
}

do_install () {
    install -d ${D}${base_libdir}/modules/wlan/prima
    install -m 0644 ${S}/wlan.ko ${D}${base_libdir}/modules/wlan/prima
}