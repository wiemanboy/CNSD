package types

type ReservationEvent struct {
	Message string `json:"message"`
	Id      string `json:"id"`
	Status  string `json:"status"`
}
