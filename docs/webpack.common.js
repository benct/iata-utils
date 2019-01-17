const path = require('path');

const PATHS = {
    app: path.join(__dirname, 'src'),
    dist: path.join(__dirname, 'dist'),
};

module.exports = {
    context: PATHS.app,
    entry: ['@babel/polyfill', path.join(PATHS.app, 'index.js')],
    output: {
        path: PATHS.dist,
        filename: 'bundle.js',
        publicPath: '/',
    },
    resolve: {
        extensions: ['.js', '.jsx'],
    },
    module: {
        rules: [
            {
                test: /\.jsx?$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader',
                    options: {
                        presets: ['@babel/preset-env', '@babel/preset-react'],
                    },
                },
            }
        ],
    },
};
