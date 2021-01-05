import React from 'react';
import {io} from "summer-shared";

type AboutComponentState = io.adev.summer.example.presentation.AboutView

export class AboutComponent extends React.Component<{}, AboutComponentState> {

    private viewModel: io.adev.summer.example.presentation.AboutInput

    constructor(props: {}) {
        super(props);
        this.viewModel = io.adev.summer.example.ServiceLocator.aboutViewModel()
    }

    // The tick function sets the current state. TypeScript will let us know
    // which ones we are allowed to set.
    tick() {
        this.setState({
            isLoading: !this.state?.isLoading ?? false
        });
    }

    // Before the component mounts, we initialise our state
    componentWillMount() {
        this.tick();
    }

    // After the component did mount, we set the state each second.
    componentDidMount() {
        setInterval(() => this.tick(), 1000);
    }

    // render will know everything!
    render() {
        return <p>The current time is {this.state.isLoading}</p>
    }
}

export default AboutComponent