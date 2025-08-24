{{- define "travelwithme-mysql.fullname" -}}
{{- .Chart.Name | lower -}}
{{- end }}

{{- define "travelwithme-mysql.labels" -}}
app.kubernetes.io/name: {{ include "travelwithme-mysql.fullname" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{- define "travelwithme-mysql.selectorLabels" -}}
app.kubernetes.io/name: {{ include "travelwithme-mysql.fullname" . }}
{{- end }}
