DESCRIPTION = "OpenMAX core for MSM chipsets"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=3775480a712fc46a69647678acb234cb"

SRC_URI = "file://${WORKSPACE}/mm-video-oss/mm-core"

inherit autotools

PR = "r8"

S = "${WORKDIR}/mm-core"

LV = "1.0.0"

PACKAGE_ARCH = "${MACHINE_ARCH}"

EXTRA_OECONF_append = "${@base_conditional('MACHINE', 'msm8655', ' --enable-target=msm7630', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('MACHINE', 'msm7627a', ' --enable-target=msm7627A', '', d)}"
EXTRA_OECONF_append = "${@base_conditional('MACHINE', 'msm8960', ' --enable-target=msm8960', '', d)}"

FILES_${PN} = "\
    /usr/lib/* \
    /usr/bin/*"

#Skips check for .so symlinks
INSANE_SKIP_${PN} = "dev-so"

do_install() {
	oe_runmake DESTDIR="${D}/" LIBVER="${LV}" install
}
