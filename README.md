# Pedestal Component with config

This is a minimal example to get started with:

- a component based system
- pedestal
- passing configuration with Aero
- restarting the system when developing with nREPL

Besides it is ready to be distributed:

- `clj -T:build uber` to build a uberjar
- `docker build -t <name-your-service> .` to build the docker container
- `docker run -p 8890:8890 <name-your-service>` to run it

Or use a docker-compose file with `docker compose up`
