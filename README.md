[![Project status](https://badgen.net/badge/project%20status/stable%20%26%20actively%20maintaned?color=green)](https://github.com/homecentr/docker-prometheus-blackbox-exporter/graphs/commit-activity) [![](https://badgen.net/github/label-issues/homecentr/docker-prometheus-blackbox-exporter/bug?label=open%20bugs&color=green)](https://github.com/homecentr/docker-prometheus-blackbox-exporter/labels/bug) [![](https://badgen.net/github/release/homecentr/docker-prometheus-blackbox-exporter)](https://hub.docker.com/repository/docker/homecentr/prometheus-blackbox-exporter)
[![](https://badgen.net/docker/pulls/homecentr/prometheus-blackbox-exporter)](https://hub.docker.com/repository/docker/homecentr/prometheus-blackbox-exporter) 
[![](https://badgen.net/docker/size/homecentr/prometheus-blackbox-exporter)](https://hub.docker.com/repository/docker/homecentr/prometheus-blackbox-exporter)

![CI/CD on master](https://github.com/homecentr/docker-prometheus-blackbox-exporter/workflows/CI/CD%20on%20master/badge.svg)
![Regular Docker image vulnerability scan](https://github.com/homecentr/docker-prometheus-blackbox-exporter/workflows/Regular%20Docker%20image%20vulnerability%20scan/badge.svg)


# HomeCentr - prometheus-blackbox-exporter

This Docker image is a repack of the original [Prometheus Blackbox exporter](https://github.com/prometheus/blackbox_exporter) with the usual Homecentr bells and whistles.

## Usage

```yml
version: "3.7"
services:
  prometheus-blackbox-exporter:
    build: .
    image: homecentr/prometheus-blackbox-exporter
    ports:
      - 9115:9115
    volumes:
      - ./example:/config
    restart: unless-stopped
```

## Environment variables

| Name | Default value | Description |
|------|---------------|-------------|
| PUID | 7077 | UID of the user prometheus-blackbox-exporter should be running as. |
| PGID | 7077 | GID of the user prometheus-blackbox-exporter should be running as. |
| BLACKBOX_EXPORTER_ARGS | | Additional command line arguments passed to the Blackbox exporter executable |

## Exposed ports

| Port | Protocol | Description |
|------|------|-------------|
| 9115 | TCP | Metrics served over HTTP by the Blackbox exporter |

## Volumes

| Container path | Description |
|------------|---------------|
| /config | Directory containing [Blackbox exporter configuration](https://github.com/prometheus/blackbox_exporter/blob/master/CONFIGURATION.md). The config file should /config/config.yml. Make sure the file uses LF (i.e. Unix) line endings. |

## Security
The container is regularly scanned for vulnerabilities and updated. Further info can be found in the [Security tab](https://github.com/homecentr/docker-prometheus-blackbox-exporter/security).

### Container user
The container supports privilege drop. Even though the container starts as root, it will use the permissions only to perform the initial set up. The prometheus-blackbox-exporter process runs as UID/GID provided in the PUID and PGID environment variables.

:warning: Do not change the container user directly using the `user` Docker compose property or using the `--user` argument. This would break the privilege drop logic.