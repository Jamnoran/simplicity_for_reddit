---
title: Release candidate checklist for {{ env.VERSION_NUMBER }}
labels: release-checklist
---

{{ payload.sender.login }} just created a new RC branch.

## Checklist for release candidate {{ env.VERSION_NUMBER }}:

- [ ] Make sure issues in Jira are tagged with `{{ env.VERSION_NUMBER }}`
- [ ] Notify QA that version `{{ env.VERSION_NUMBER }}` is available at Firebase
- [ ] Notify beta testers if needed
- [ ] QA has finalized testing
- [ ] Communicate to operations that app is ready to be published