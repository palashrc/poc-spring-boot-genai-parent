# `/configuration` directory

`/configuration/files` directory contains templates and smoke test files that
are used in application deployment.

It contains:

* `helm` directory - files used in helm chart defined in `app_deployment.charts`.
  [Deployment order](https://github.com/helm/helm/blob/release-3.0/pkg/releaseutil/kind_sorter.go#L27)

  * `deployment.yaml` - file responsible for application deployment. It
    contains also service with color that exposes application

  * `hpa.yaml` - file responsible for horizontal pod autoscaler template

  * `configmaps.yaml` - file responsible for ConfigMap created from
    {env}/app-config directory

  * `secrets.yaml` - file responsible for Secret created from {env}/app-secrets directory

* `service.yaml` - file responsible for exposing application through service. It
  points to color service defined in deployment.yaml

* `virtualservice.yaml` - file responsible for exposing application through
  virtualservice. It points to service defined in service.yaml

* `postman.GCP-Dev.json` - smoke test file that needs to follow convention
  `postman.<environment>.json where` "environment" is name of specific directory
  in `vars/env/`

* `monitoring` - monitoring templates location with some dummy small examples,
  look here for a real production-like templates:
  <https://git.prod.sabre.com/projects/NGP-MS/repos/ngp-monitoring-gcp-examples/browse>
