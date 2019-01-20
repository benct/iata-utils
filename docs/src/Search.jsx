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
        if (this.state.query.length === 3) {
            this.search(this.state.query);
        }
    }

    search(query) {
        return fetch(`https://tomlin.no/api/?service=iata&action=search&query=${query}`)
            .then(response => (response.ok ? response.json() : Promise.reject(response.statusText)))
            .then(response => this.setState({ data: response }))
            .catch(error => console.log(error)); // eslint-disable-line
    }

    static renderItem(data, idx) {
        return (
            <div className="app-result" key={`item${idx}`}>
                <h3>{data.name}</h3>
                {data.type} | <small>IATA</small> <strong>{data.iataCode}</strong> | <small>ICAO</small> <strong>{data.icaoCode}</strong>
                <br />
                {data.cityName} ({data.cityCode})
                <br />
                {data.area} ({data.areaCode}), {data.country} ({data.countryCode}), {data.continent}
                <br />
                IANA Timezone: {data.timezone}
                <br />
                Coordinates: {data.latitude}, {data.longitude}
                <br />
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
                        placeholder="3-letter IATA Code"
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
                {this.state.data.length ? this.state.data.map(Search.renderItem) : <span>No results</span>}
                <hr />
            </>
        );
    }
}
