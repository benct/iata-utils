import React from 'react';

export default class Search extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            query: '',
            data: [],
        };
    }

    handleKey(event) {
        if (event.keyCode === 13 || event.key === 'Enter') {
            this.handleSearch(event);
        }
    }

    handleChange(event) {
        this.setState({ query: event.target.value });
    }

    handleSearch() {
        if (this.state.query.length === 2 || this.state.query.length === 3) {
            this.search(this.state.query);
        }
    }

    search(query) {
        return fetch(`https://tomlin.no/api/?service=iata&action=search&query=${query}`)
            .then(response => (response.ok ? response : Promise.reject(`${response.status}: ${response.statusText}`)))
            .then(response => (response.status === 200 ? response.json() : []))
            .then(response => this.setState({ data: response }))
            .catch(error => console.log(error)); // eslint-disable-line
    }

    static renderAirline(data) {
        return (
            <div className="app-result-margin">
                {data.alias !== '' ? (
                    <div>
                        Alias: <code>{data.alias}</code>
                    </div>
                ) : null}
                {data.started !== '' ? (
                    <div>
                        Started: <code>{data.started}</code>
                    </div>
                ) : null}
                {data.ended !== '' ? (
                    <div>
                        Ended: <code>{data.ended}</code>
                    </div>
                ) : null}
            </div>
        );
    }

    static renderLocation(data) {
        return (
            <div className="app-result-margin">
                <code>
                    {data.cityName} ({data.cityCode})
                </code>
                <br />
                <code>
                    {data.area !== '' ? `${data.area} (${data.areaCode}), ` : null}
                    {data.country} ({data.countryCode}), {data.continent}
                </code>
                <br />
                <code>{data.timezone}</code>
                <br />
                <code>
                    {data.latitude}, {data.longitude}
                </code>
            </div>
        );
    }

    static renderItem(data, idx) {
        return (
            <div className="app-result" key={`item${idx}`}>
                <h3>
                    {data.name}&nbsp;&nbsp;<small>{data.type}</small>
                </h3>
                <div className="app-result-margin">
                    <small>IATA</small> <strong>{data.iataCode}</strong> | <small>ICAO</small> <strong>{data.icaoCode || '-'}</strong>
                </div>
                {data.iataCode.length === 2 ? Search.renderAirline(data) : Search.renderLocation(data)}
                <a href={data.wiki}>{data.wiki}</a>
            </div>
        );
    }

    render() {
        return (
            <>
                <label htmlFor="query">Enter IATA code and press enter</label>
                <div className="app-input">
                    <input
                        className="app-input-effect"
                        type="text"
                        placeholder="3-letter location code or 2-letter airline code"
                        id="query"
                        aria-label="Search"
                        maxLength="3"
                        value={this.state.query}
                        onClick={event => event.target.select()}
                        onChange={this.handleChange.bind(this)}
                        onKeyPress={this.handleKey.bind(this)}
                    />
                    <span className="focus-border">
                        <i />
                    </span>
                </div>
                {this.state.data.length ? this.state.data.map(Search.renderItem) : null}
                <hr />
            </>
        );
    }
}
