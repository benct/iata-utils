import React from 'react';
import ReactDOM from 'react-dom';

import Search from './Search.jsx';

ReactDOM.render(<Search />, document.getElementById('app-root'));

if (module.hot) {
    module.hot.accept();
}
