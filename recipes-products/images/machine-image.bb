# List of packages installed onto the root file system as specified by the user.
require ${BASEMACHINE}/${BASEMACHINE}-base-image.inc
require mdm-bootimg.inc

inherit core-image

MULTILIBRE_ALLOW_REP =. "/usr/include/python2.7/*|${base_bindir}|${base_sbindir}|${bindir}|${sbindir}|${libexecdir}|${sysconfdir}|${nonarch_base_libdir}/udev|/lib/modules/[^/]*/modules.*|"
