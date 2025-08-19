{{- define "backend-with-db.fullname" -}}
{{- .Chart.Name | lower -}}
{{- end }}

{{- define "backend-with-db.labels" -}}
app.kubernetes.io/name: {{ include "backend-with-db.fullname" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{- define "backend-with-db.selectorLabels" -}}
app.kubernetes.io/name: {{ include "backend-with-db.fullname" . }}
{{- end }}
