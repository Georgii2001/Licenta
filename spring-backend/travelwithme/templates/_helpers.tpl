{{- define "travelwithme-backend.fullname" -}}
{{- .Chart.Name | lower -}}
{{- end }}

{{- define "travelwithme-backend.labels" -}}
app.kubernetes.io/name: {{ include "travelwithme-backend.fullname" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{- define "travelwithme-backend.selectorLabels" -}}
app.kubernetes.io/name: {{ include "travelwithme-backend.fullname" . }}
{{- end }}
