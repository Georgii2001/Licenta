#! /bin/sh
# 1) Login – ia token
BASE_URL="http://$(minikube ip):30080"
EMAIL="test@example.com"; PASS="secret"
TOKEN=$(curl -s -X POST "$BASE_URL/api/auth/login" -H 'Content-Type: application/json' \
  -d "{\"email\":\"$EMAIL\",\"password\":\"$PASS\"}" | jq -r '.token')

# 2) Rulează 200 de cereri search cu 5 „useri” în paralel și măsoară time_total
N=10; ITER=200  # total 200 requests
export BASE_URL TOKEN
rm -f times.txt
for i in $(seq 1 $N); do
  (
    for j in $(seq 1 $ITER); do
      curl -s -o /dev/null -w "%{time_total}\n" \
        -H "Authorization: Bearer $TOKEN" \
        "$BASE_URL/api/users/search?q=anna" >> times.txt
    done
  ) &
done
wait

# 3) Statistici rapide: count, min, median, p95, max
awk '{
  x[NR]=$1; s+=$1
}
END{
  n=NR; asort(x)
  p50=x[int(0.5*n)]; p95=x[int(0.95*n)]
  printf("count=%d min=%.3f median(p50)=%.3f p95=%.3f max=%.3f\n", n, x[1], p50, p95, x[n])
}' times.txt
