inherit autotools

DESCRIPTION = "Bluetooth application layer"
LICENSE = "Apache-2.0"
HOMEPAGE = "https://www.codeaurora.org/"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/\
${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://qcom-opensource/bt/bt-app/"

S = "${WORKDIR}/qcom-opensource/bt/bt-app/"

DEPENDS += "libhardware"

CPPFLAGS_append = " -DBT_AUDIO_HAL_INTEGRATION"

EXTRA_OECONF = " \
                --with-common-includes="${WORKSPACE}/hardware/libhardware/include" \
               "
do_install_append() {
        install -d ${D}${sysconfdir}/bluetooth/

        if [ -f ${S}conf/bt_app.conf ]; then
           install -m 0660 ${S}conf/bt_app.conf ${D}${sysconfdir}/bluetooth/
        fi
}