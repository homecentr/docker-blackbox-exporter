FROM prom/blackbox-exporter:v0.17.0 as original

FROM homecentr/base:3.4.0-alpine

ENV BLACKBOX_EXPORTER_ARGS=""

COPY --from=original /bin/blackbox_exporter /bin/blackbox_exporter
COPY --from=original /etc/blackbox_exporter/config.yml /config/config.yml

COPY ./fs/ /

RUN chmod a+x /bin/blackbox_exporter

VOLUME [ "/config" ]

EXPOSE 9115