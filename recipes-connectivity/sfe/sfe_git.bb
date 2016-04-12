inherit autotools-brokensep module
DESCRIPTION = "Shortcut Forward Engine Driver"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=f3b90e78ea0cffb20bf5cca7947a896d"

FILESPATH =+ "${WORKSPACE}:"
SRC_URI = "file://shortcut-fe/shortcut-fe/ "

S = "${WORKDIR}/shortcut-fe/shortcut-fe"

do_install() {
    module_do_install
}
