# Monitoring directory structure

Monitoring directory can contain following directories:

* `common` - monitoring templates for all environments

* `<env>` - monitoring templates for any particular environment
  (however rather as an exception, since the set of alerts should be the same
  for all ENVs (common directory) and modified by externalized properties
  (to avoid huge copy pastes))
