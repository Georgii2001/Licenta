{{- define "travelwithme-frontend.fullname" -}}
{{- .Chart.Name | lower -}}
{{- end }}

{{- define "travelwithme-frontend.labels" -}}
app.kubernetes.io/name: {{ include "travelwithme-frontend.fullname" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{- define "travelwithme-frontend.selectorLabels" -}}
app.kubernetes.io/name: {{ include "travelwithme-frontend.fullname" . }}
{{- end }}
