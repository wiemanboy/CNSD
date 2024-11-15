package ReservationStatus

type ReservationStatus string

const (
	Failed    ReservationStatus = "FAILED"
	Success   ReservationStatus = "SUCCESS"
	Canceled  ReservationStatus = "CANCELED"
	Confirmed ReservationStatus = "CONFIRMED"
)
