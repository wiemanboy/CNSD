package types

type Reservation struct {
	Id       string `json:"id"`
	Location string `json:"location"`
	Date     string `json:"date"`
	Status   string `json:"status"`
}
