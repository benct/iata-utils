import React from 'react';
import ReactDOM from 'react-dom';

const App = () => {
    return (
        <div>react test</div>
    );
};

ReactDOM.render(<App />, document.getElementById('app-root'));

if (module.hot) {
    module.hot.accept();
}
